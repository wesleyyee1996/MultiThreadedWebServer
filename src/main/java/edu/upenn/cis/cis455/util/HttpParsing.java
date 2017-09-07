/*
 * #%L
 * NanoHttpd-Core
 * %%
 * Copyright (C) 2012 - 2016 nanohttpd
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the nanohttpd nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package edu.upenn.cis.cis455.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletResponse;

import edu.upenn.cis.cis455.exceptions.ClosedConnectionException;
import edu.upenn.cis.cis455.exceptions.HaltException;


/**
 * Header parsing help, largely derived from NanoHttpd, copyright notice above.
 */
public class HttpParsing {
    final static Logger logger = LogManager.getLogger(HttpParsing.class);
    
    /**
     * Initial fetch buffer for the HTTP request header
     * 
     */
    static final int BUFSIZE = 8192;
    
    
    /**
     * Decodes the sent headers and loads the data into Key/value pairs
     */
    public static void decodeHeader(BufferedReader in, Map<String, String> pre, Map<String, List<String>> parms, Map<String, String> headers) throws HaltException {
        try {
            // Read the request line
            String inLine = in.readLine();
            if (inLine == null) {
                return;
            }

            StringTokenizer st = new StringTokenizer(inLine);
            if (!st.hasMoreTokens()) {
                throw new HaltException(HttpServletResponse.SC_BAD_REQUEST, "BAD REQUEST: Syntax error. Usage: GET /example/file.html");
            }

            pre.put("method", st.nextToken());

            if (!st.hasMoreTokens()) {
                throw new HaltException(HttpServletResponse.SC_BAD_REQUEST, "BAD REQUEST: Missing URI. Usage: GET /example/file.html");
            }

            String uri = st.nextToken();
            String queryString = "";

            // Decode parameters from the URI
            int qmi = uri.indexOf('?');
            if (qmi >= 0) {
                queryString = uri.substring(qmi + 1);
                decodeParms(uri.substring(qmi + 1), parms);
                uri = decodePercent(uri.substring(0, qmi));
            } else {
                uri = decodePercent(uri);
            }
            

            // If there's another token, its protocol version,
            // followed by HTTP headers.
            // NOTE: this now forces header names lower case since they are
            // case insensitive and vary by client.
            if (st.hasMoreTokens()) {
                pre.put("protocolVersion", st.nextToken());
            } else {
                pre.put("protocolVersion", "HTTP/1.1");
                logger.debug("No protocol version specified, strange. Assuming HTTP/1.1.");
            }
            String line = in.readLine();
            while (line != null && !line.trim().isEmpty()) {
                int p = line.indexOf(':');
                if (p >= 0) {
                    headers.put(line.substring(0, p).trim().toLowerCase(Locale.US), line.substring(p + 1).trim());
                }
                line = in.readLine();
            }

            pre.put("uri", uri);
            pre.put("queryString", queryString);
        } catch (IOException ioe) {
            throw new HaltException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
        }
    }
    
    /**
     * Decodes parameters in percent-encoded URI-format ( e.g.
     * "name=Jack%20Daniels&pass=Single%20Malt" ) and adds them to given Map.
     */
    public static String decodeParms(String parms, Map<String, List<String>> p) {
        String queryParameterString = "";
        
        if (parms == null) {
            return queryParameterString;
        }

        queryParameterString = parms;
        StringTokenizer st = new StringTokenizer(parms, "&");
        while (st.hasMoreTokens()) {
            String e = st.nextToken();
            int sep = e.indexOf('=');
            String key = null;
            String value = null;

            if (sep >= 0) {
                key = decodePercent(e.substring(0, sep)).trim();
                value = decodePercent(e.substring(sep + 1));
            } else {
                key = decodePercent(e).trim();
                value = "";
            }

            List<String> values = p.get(key);
            if (values == null) {
                values = new ArrayList<String>();
                p.put(key, values);
            }

            values.add(value);
        }
        
        return queryParameterString;
    }


    /**
     * Decode percent encoded <code>String</code> values.
     * 
     * @param str
     *            the percent encoded <code>String</code>
     * @return expanded form of the input, for example "foo%20bar" becomes
     *         "foo bar"
     */
    public static String decodePercent(String str) {
        String decoded = null;
        try {
            decoded = URLDecoder.decode(str, "UTF8");
        } catch (UnsupportedEncodingException ignored) {
            logger.warn("Encoding not supported, ignored", ignored);
        }
        return decoded;
    }
    
    /**
     * Parse the initial request header
     * 
     * @param remoteIp IP address of client
     * @param inputStream Socket input stream (not yet read)
     * @param headers Map to receive header key/values
     * @param parms Map to receive parameter key/value-lists
     */
    public static String parseRequest(
                        String remoteIp, 
                        InputStream inputStream, 
                        Map<String, String> headers,
                        Map<String, List<String>> parms) throws IOException, HaltException {
        int splitbyte = 0;
        int rlen = 0;
        String uri = "";
        
        try {
            // Read the first 8192 bytes.
            // The full header should fit in here.
            // Apache's default header limit is 8KB.
            // Do NOT assume that a single read will get the entire header
            // at once!
            byte[] buf = new byte[BUFSIZE];
            splitbyte = 0;
            rlen = 0;

            int read = -1;
            inputStream.mark(BUFSIZE);
            try {
                read = inputStream.read(buf, 0, BUFSIZE);
            } catch (IOException e) {
                throw new ClosedConnectionException();
            }
            if (read == -1) {
                throw new HaltException(HttpServletResponse.SC_BAD_REQUEST);
            }
            while (read > 0) {
                rlen += read;
                splitbyte = findHeaderEnd(buf, rlen);
                if (splitbyte > 0) {
                    break;
                }
                read = inputStream.read(buf, rlen, BUFSIZE - rlen);
            }

            if (splitbyte < rlen) {
                inputStream.reset();
                inputStream.skip(splitbyte);
            }

            headers.clear();

            // Create a BufferedReader for parsing the header.
            BufferedReader hin = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buf, 0, rlen)));

            // Decode the header into parms and header java properties
            Map<String, String> pre = new HashMap<String, String>();
            decodeHeader(hin, pre, parms, headers);

            if (null != remoteIp) {
                headers.put("remote-addr", remoteIp);
                headers.put("http-client-ip", remoteIp);
            }

            uri = pre.get("uri") + (pre.get("queryString").isEmpty() ? "" : "?" + pre.get("queryString"));
            
            headers.put("protocolVersion", pre.get("protocolVersion"));
            
            headers.put("Method", pre.get("method"));

//            this.cookies = new CookieHandler(this.headers);

//            String connection = headers.get("connection");
//            boolean keepAlive = "HTTP/1.1".equals(pre.get("protocolVersion") )
//                && (connection == null || !connection.matches("(?i).*close.*"));

//            // Ok, now do the serve()
//
//            // TODO: long body_size = getBodySize();
//            // TODO: long pos_before_serve = this.inputStream.totalRead()
//            // (requires implementation for totalRead())
//            r = httpd.handle(this);
//            // TODO: this.inputStream.skip(body_size -
//            // (this.inputStream.totalRead() - pos_before_serve))
//
//            if (r == null) {
//                throw new HaltException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SERVER INTERNAL ERROR: Serve() returned a null response.");
//            } else {
//                String acceptEncoding = this.headers.get("accept-encoding");
//                this.cookies.unloadQueue(r);
//                r.setRequestMethod(this.method);
//                if (acceptEncoding == null || !acceptEncoding.contains("gzip")) {
//                    r.setUseGzip(false);
//                }
//                r.setKeepAlive(keepAlive);
//                r.send(this.outputStream);
//            }
//            if (!keepAlive || r.isCloseConnection()) {
//                throw new SocketException("NanoHttpd Shutdown");
//            }
        } catch (SocketException e) {
            // throw it out to close socket object (finalAccept)
            throw new ClosedConnectionException();
        } catch (SocketTimeoutException ste) {
            // treat socket timeouts the same way we treat socket exceptions
            // i.e. close the stream & finalAccept object by throwing the
            // exception up the call stack.
            throw new ClosedConnectionException();
        }
        
        return uri;
    }
    
    /**
     * Find byte index separating header from body. It must be the last byte of
     * the first two sequential new lines.
     */
    static int findHeaderEnd(final byte[] buf, int rlen) {
        int splitbyte = 0;
        while (splitbyte + 1 < rlen) {

            // RFC2616
            if (buf[splitbyte] == '\r' && buf[splitbyte + 1] == '\n' && splitbyte + 3 < rlen && buf[splitbyte + 2] == '\r' && buf[splitbyte + 3] == '\n') {
                return splitbyte + 4;
            }

            // tolerance
            if (buf[splitbyte] == '\n' && buf[splitbyte + 1] == '\n') {
                return splitbyte + 2;
            }
            splitbyte++;
        }
        return 0;
    }

    public static String explainStatus(int statusCode) {
        switch (statusCode) {
        case HttpServletResponse.SC_ACCEPTED:
            return "Accepted";
        case HttpServletResponse.SC_BAD_GATEWAY:
            return "Bad Gateway";
        case HttpServletResponse.SC_BAD_REQUEST:
            return "Bad Request";
        case HttpServletResponse.SC_CONFLICT:
            return "Conflict";
        case HttpServletResponse.SC_CONTINUE:
            return "Continue";
        case HttpServletResponse.SC_CREATED:
            return "Created";
        case HttpServletResponse.SC_EXPECTATION_FAILED:
            return "Expectation Failed";
        case HttpServletResponse.SC_FORBIDDEN:
            return "Forbidden";
        case HttpServletResponse.SC_GATEWAY_TIMEOUT:
            return "Gateway Timeout";
        case HttpServletResponse.SC_GONE:
            return "Gone";
        case HttpServletResponse.SC_HTTP_VERSION_NOT_SUPPORTED:
            return "HTTP Version Not Supported";
        case HttpServletResponse.SC_INTERNAL_SERVER_ERROR:
            return "Internal Server Error";
        case HttpServletResponse.SC_LENGTH_REQUIRED:
            return "Length Required";
        case HttpServletResponse.SC_METHOD_NOT_ALLOWED:
            return "Method Not Allowed";
        case HttpServletResponse.SC_MOVED_PERMANENTLY:
            return "Moved Permanently";
        case HttpServletResponse.SC_MOVED_TEMPORARILY:
            return "Moved Temporarily";
        case HttpServletResponse.SC_MULTIPLE_CHOICES:
            return "Multiple Choices";
        case HttpServletResponse.SC_NO_CONTENT:
            return "No Content";
        case HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION:
            return "Non-Authoritative Information";
        case HttpServletResponse.SC_NOT_ACCEPTABLE:
            return "Not Acceptable";
        case HttpServletResponse.SC_NOT_FOUND:
            return "Not Found";
        case HttpServletResponse.SC_NOT_IMPLEMENTED:
            return "Not Implemented";
        case HttpServletResponse.SC_NOT_MODIFIED:
            return "Not Modified";
        case HttpServletResponse.SC_OK:
            return "OK";
        case HttpServletResponse.SC_PARTIAL_CONTENT:
            return "Partial Content";
        case HttpServletResponse.SC_PAYMENT_REQUIRED:
            return "Payment Required";
        case HttpServletResponse.SC_PRECONDITION_FAILED:
            return "Precondition Failed";
        case HttpServletResponse.SC_PROXY_AUTHENTICATION_REQUIRED:
            return "Proxy Authentication Required";
        case HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE:
            return "Request Entity Too Large";
        case HttpServletResponse.SC_REQUEST_TIMEOUT:
            return "Request Timeout";
        case HttpServletResponse.SC_REQUEST_URI_TOO_LONG:
            return "Request URI Too Long";
        case HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE:
            return "Requested Range Not Satisfiable";
        case HttpServletResponse.SC_RESET_CONTENT:
            return "Reset Content";
        default:
            return "Unknown error";
        }
    }

    public static String getMimeType(String path) {
        if (path == null)
            return "";
        
        if (path.endsWith(".html") || path.endsWith("htm"))
            return "text/html";
        if (path.endsWith("txt") || path.endsWith("text"))
            return "text/plain";
        if (path.endsWith("gif"))
            return "image/gif";
        if (path.endsWith("jpg") || path.endsWith("jpeg"))
            return "image/jpeg";
            
        return "octet/stream";
    }
}
