/**
 * CIS 455/555 route-based HTTP framework.
 * 
 * The HaltException, when thrown, should end the code being
 * handled in an HTTP thread, and should force an HTTP response.
 * 
 * Z. Ives, 8/2017
 * 
 * Portions excerpted from or inspired by Spark Framework, 
 * 
 *                 http://sparkjava.com,
 * 
 * with license notice included below.
 */

/*
 * Copyright 2011- Per Wendel
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.upenn.cis.cis455.exceptions;

import javax.servlet.http.HttpServletResponse;

public class HaltException extends RuntimeException {
    int statusCode = HttpServletResponse.SC_OK;
    private String body = null;
    
    public HaltException() {
        super();
    }
    
    public HaltException(int statusCode) {
        statusCode = statusCode;
    }
    
    public HaltException(String body) {
        this.body = body;
    }
    
    public HaltException(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }
    
    public int statusCode() {
        return statusCode;
    }
    
    public String body() {
        return body;
    }
}
