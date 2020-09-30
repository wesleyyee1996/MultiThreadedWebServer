package edu.upenn.cis.test;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import edu.upenn.cis.cis455.m1.server.CommandLineParser;
import edu.upenn.cis.cis455.m1.server.CommandLineValues;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommandLineParserTest {
	private boolean success = true;
	
		@Test
	public void test1() {
		String[] args = {"45555","/"};
		CommandLineParser cmdLineParse = new CommandLineParser();
		CommandLineValues.getInstance().resetValues();
		cmdLineParse.validateInput(args, CommandLineValues.getInstance());
		assertTrue(CommandLineValues.getInstance().getValuesOk());
	}
	
	@Test
	public void test2() {
		String[] args = {"45555","./"};
		CommandLineParser cmdLineParse = new CommandLineParser();
		CommandLineValues.getInstance().resetValues();
		cmdLineParse.validateInput(args, CommandLineValues.getInstance());
		assertTrue(CommandLineValues.getInstance().getValuesOk());
	}
	
	@Test
	public void test3() {
		String[] args = {"45555","/.www"};
		CommandLineParser cmdLineParse = new CommandLineParser();
		CommandLineValues.getInstance().resetValues();
		cmdLineParse.validateInput(args, CommandLineValues.getInstance());
		assertFalse(CommandLineValues.getInstance().getValuesOk());
	}
	
	@Test
	public void test4() {
		String[] args = {"45555","."};
		CommandLineParser cmdLineParse = new CommandLineParser();
		CommandLineValues.getInstance().resetValues();
		cmdLineParse.validateInput(args, CommandLineValues.getInstance());
		assertFalse(CommandLineValues.getInstance().getValuesOk());
	}
	
	@Test
	public void test5() {
		String[] args = {"45555","/.www","abc"};
		CommandLineParser cmdLineParse = new CommandLineParser();
		CommandLineValues.getInstance().resetValues();
		cmdLineParse.validateInput(args, CommandLineValues.getInstance());
		assertFalse(CommandLineValues.getInstance().getValuesOk());
	}
	
	@Test
	public void test6() {
		String[] args = {"45555"};
		CommandLineParser cmdLineParse = new CommandLineParser();
		CommandLineValues.getInstance().resetValues();
		cmdLineParse.validateInput(args, CommandLineValues.getInstance());
		assertTrue(CommandLineValues.getInstance().getValuesOk());
	}
	
	@Test
	public void test7() {
		String[] args = {"455555"};
		CommandLineParser cmdLineParse = new CommandLineParser();
		CommandLineValues.getInstance().resetValues();
		cmdLineParse.validateInput(args, CommandLineValues.getInstance());
		assertFalse(CommandLineValues.getInstance().getValuesOk());
	}
	
	@Test
	public void test8() {
		String[] args = {"/"};
		CommandLineParser cmdLineParse = new CommandLineParser();
		CommandLineValues.getInstance().resetValues();
		cmdLineParse.validateInput(args, CommandLineValues.getInstance());
		assertTrue(CommandLineValues.getInstance().getValuesOk());
	}
	
	@Test
	public void test9() {
		String[] args = {"80"};
		CommandLineParser cmdLineParse = new CommandLineParser();
		CommandLineValues.getInstance().resetValues();
		cmdLineParse.validateInput(args, CommandLineValues.getInstance());
		assertFalse(CommandLineValues.getInstance().getValuesOk());
	}
	
	@Test
	public void test10() {
		String[] args = {"0"};
		CommandLineParser cmdLineParse = new CommandLineParser();
		CommandLineValues.getInstance().resetValues();
		cmdLineParse.validateInput(args, CommandLineValues.getInstance());
		assertFalse(CommandLineValues.getInstance().getValuesOk());
	}
	
	@Test
	public void test11() {
		String[] args = {"/vagrant/cis555-key"};
		CommandLineParser cmdLineParse = new CommandLineParser();
		CommandLineValues.getInstance().resetValues();
		cmdLineParse.validateInput(args, CommandLineValues.getInstance());
		assertFalse(CommandLineValues.getInstance().getValuesOk());
	}
	
	@Test
	public void test12() {
		String[] args = {"//vagrant/cis555-key"};
		CommandLineParser cmdLineParse = new CommandLineParser();
		CommandLineValues.getInstance().resetValues();
		cmdLineParse.validateInput(args, CommandLineValues.getInstance());
		assertFalse(CommandLineValues.getInstance().getValuesOk());
	}
	
	@Test
	public void test13() {
		String[] args = {"45555 /vagrant"};
		CommandLineParser cmdLineParse = new CommandLineParser();
		CommandLineValues.getInstance().resetValues();
		cmdLineParse.validateInput(args, CommandLineValues.getInstance());
		assertFalse(CommandLineValues.getInstance().getValuesOk());
	}

}
