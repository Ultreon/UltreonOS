/*
 * $Id$
 *
 * Copyright (C) 2020-2022 Ultreon Team
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; If not, write to the Free Software Foundation, Inc., 
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
 
package org.jnode.test.shell.bjorne;

import org.jnode.shell.ShellException;
import org.jnode.shell.ShellSyntaxException;
import org.jnode.shell.bjorne.BjorneArithmeticEvaluator;
import org.jnode.shell.bjorne.BjorneContext;
import org.jnode.shell.io.CommandIOHolder;
import org.junit.Assert;
import org.junit.Test;

/**
 * Some unit tests for the BjorneArithmeticEvaluator class.
 * 
 * @author crawley@jnode.org
 */
public class BjorneArithmeticEvaluatorTest {

    // This class simply allows us to call the setVariable method directly
    private static class TestBjorneContext extends BjorneContext {
        TestBjorneContext(CommandIOHolder[] holders) {
            super(null, holders);
        }

        TestBjorneContext() {
            super(null, null);
        }

        @Override
        protected void setVariable(String name, String value) {
            super.setVariable(name, value);
        }

        @Override
        protected String variable(String name) throws ShellSyntaxException {
            return super.variable(name);
        }
    }

    private static class TestBjorneArithmeticEvaluator extends BjorneArithmeticEvaluator {
        public TestBjorneArithmeticEvaluator(BjorneContext context) {
            super(context);
        }

        @Override
        public synchronized String evaluateExpression(CharSequence source) throws ShellException {
            return super.evaluateExpression(source);
        }
    }

    @Test
    public void testConstructor() {
        new BjorneArithmeticEvaluator(new TestBjorneContext());
    }

    @Test
    public void testLiterals() throws ShellException {
        TestBjorneArithmeticEvaluator ev =
                new TestBjorneArithmeticEvaluator(new TestBjorneContext());
        Assert.assertEquals("1", ev.evaluateExpression("1"));
        Assert.assertEquals("1", ev.evaluateExpression(" 1 "));
        Assert.assertEquals("42", ev.evaluateExpression("42"));
    }

    @Test
    public void testVariable() throws ShellException {
        TestBjorneContext context = new TestBjorneContext();
        context.setVariable("A", "1");
        TestBjorneArithmeticEvaluator ev = new TestBjorneArithmeticEvaluator(context);
        Assert.assertEquals("1", ev.evaluateExpression("A"));
        Assert.assertEquals("1", ev.evaluateExpression(" A "));
        Assert.assertEquals("0", ev.evaluateExpression(" B"));
    }

    @Test
    public void testUnaryPlusMinus() throws ShellException {
        TestBjorneContext context = new TestBjorneContext();
        context.setVariable("A", "1");
        TestBjorneArithmeticEvaluator ev = new TestBjorneArithmeticEvaluator(context);
        Assert.assertEquals("1", ev.evaluateExpression("+A"));
        Assert.assertEquals("1", ev.evaluateExpression(" + A "));
        Assert.assertEquals("0", ev.evaluateExpression(" + B"));
        Assert.assertEquals("-1", ev.evaluateExpression("-A"));
        Assert.assertEquals("-1", ev.evaluateExpression(" - A "));
        Assert.assertEquals("0", ev.evaluateExpression(" - B"));
    }

    @Test
    public void testInfixOperators() throws ShellException {
        TestBjorneContext context = new TestBjorneContext();
        context.setVariable("A", "1");
        TestBjorneArithmeticEvaluator ev = new TestBjorneArithmeticEvaluator(context);
        Assert.assertEquals("2", ev.evaluateExpression("1 + 1"));
        Assert.assertEquals("2", ev.evaluateExpression("A + 1"));
        Assert.assertEquals("0", ev.evaluateExpression("1 - 1"));
        Assert.assertEquals("0", ev.evaluateExpression("1 - A"));
        Assert.assertEquals("4", ev.evaluateExpression("2 * 2"));
        Assert.assertEquals("2", ev.evaluateExpression("4 / 2"));
        Assert.assertEquals("1", ev.evaluateExpression("4 % 3"));
        Assert.assertEquals("27", ev.evaluateExpression("3 ** 3"));
        try {
            ev.evaluateExpression("4 / 0");
            Assert.fail("no exception for '4 / 0'");
        } catch (ShellException ex) {
            // expected
        }
        try {
            ev.evaluateExpression("4 % 0");
            Assert.fail("no exception for '4 % 0'");
        } catch (ShellException ex) {
            // expected
        }
    }

    @Test
    public void testInfixPrecedence() throws ShellException {
        TestBjorneContext context = new TestBjorneContext();
        context.setVariable("A", "1");
        TestBjorneArithmeticEvaluator ev = new TestBjorneArithmeticEvaluator(context);
        Assert.assertEquals("0", ev.evaluateExpression("-1 * 2 + 2"));
        Assert.assertEquals("4", ev.evaluateExpression("1 * 2 + 2"));
        Assert.assertEquals("5", ev.evaluateExpression("1 + 2 * 2"));
        Assert.assertEquals("9", ev.evaluateExpression("1 + 2 * 2 ** 2"));
        Assert.assertEquals("8", ev.evaluateExpression("1 + 2 * 2 ** 2 + -A"));
    }

    @Test
    public void testParentheses() throws ShellException {
        TestBjorneContext context = new TestBjorneContext();
        context.setVariable("A", "1");
        TestBjorneArithmeticEvaluator ev = new TestBjorneArithmeticEvaluator(context);
        Assert.assertEquals("-4", ev.evaluateExpression("-1 * (2 + 2)"));
        Assert.assertEquals("4", ev.evaluateExpression("(1 * 2 + 2)"));
        Assert.assertEquals("6", ev.evaluateExpression("((1) + 2) * 2"));
        Assert.assertEquals("17", ev.evaluateExpression("1 + (2 * 2) ** 2"));
        Assert.assertEquals("10", ev.evaluateExpression("1 + 2 * 2 ** 2 + -(-1)"));
    }

    @Test
    public void testIncDec() throws ShellException {
        TestBjorneContext context = new TestBjorneContext();
        context.setVariable("A", "1");
        TestBjorneArithmeticEvaluator ev = new TestBjorneArithmeticEvaluator(context);
        Assert.assertEquals("1", ev.evaluateExpression("A++"));
        Assert.assertEquals("2", context.variable("A"));
        Assert.assertEquals("3", ev.evaluateExpression("++A"));
        Assert.assertEquals("3", context.variable("A"));
        Assert.assertEquals("3", ev.evaluateExpression("A--"));
        Assert.assertEquals("2", context.variable("A"));
        Assert.assertEquals("1", ev.evaluateExpression("--A"));
        Assert.assertEquals("1", context.variable("A"));
    }
}
