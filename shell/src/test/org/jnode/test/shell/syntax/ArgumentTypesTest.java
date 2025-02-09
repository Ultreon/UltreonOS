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
 
package org.jnode.test.shell.syntax;

import org.jnode.shell.AbstractCommand;
import org.jnode.shell.Command;
import org.jnode.shell.CommandInfo;
import org.jnode.shell.CommandLine;
import org.jnode.shell.CommandLine.Token;
import org.jnode.shell.syntax.ArgumentSyntax;
import org.jnode.shell.syntax.CommandSyntaxException;
import org.jnode.shell.syntax.EnumArgument;
import org.jnode.shell.syntax.FileArgument;
import org.junit.Assert;
import org.junit.Test;

public class ArgumentTypesTest {

    public enum TestEnum {
        ALT1, ALT2, ALT3
    }

    public static class TestArgument extends EnumArgument<TestEnum> {

        public TestArgument(String label, int flags) {
            super(label, flags, TestEnum.class);
        }

        @Override
        protected String argumentKind() {
            return "test";
        }
    }

    public static class TestEnumCommand extends AbstractCommand {
        private final EnumArgument<TestEnum> arg = new TestArgument("arg1", 0);

        public TestEnumCommand() {
            registerArguments(arg);
        }

        public void execute() throws Exception {
            getOutput().getPrintWriter().print(arg.getValue());
        }
    }

    @Test
    public void testEnumArgument() throws Exception {
        TestShell shell = new TestShell();
        shell.addAlias("command", "org.jnode.test.shell.syntax.ArgumentTypesTest$TestEnumCommand");
        shell.addSyntax("command", new ArgumentSyntax("arg1"));
        CommandLine cl =
                new CommandLine(new Token("command"), new Token[] {new Token("ALT1")}, null);
        CommandInfo cmdInfo = cl.parseCommandLine(shell);
        Command cmd = cmdInfo.createCommandInstance();
        Assert.assertEquals(TestEnum.ALT1, cmd.getArgumentBundle().getArgument("arg1").getValue());

        try {
            cl = new CommandLine(new Token("command"), new Token[] {new Token("ALT99")}, null);
            cl.parseCommandLine(shell);
            Assert.fail("parse didn't fail");
        } catch (CommandSyntaxException ex) {
            // expected
        }
        try {
            cl =
                    new CommandLine(new Token("command"), new Token[] {new Token("ALT1"),
                        new Token("ALT1")}, null);
            cl.parseCommandLine(shell);
            Assert.fail("parse didn't fail");
        } catch (CommandSyntaxException ex) {
            // expected
        }
    }

    public static class TestFileCommand extends AbstractCommand {
        private final FileArgument arg = new FileArgument("arg1", 0);

        public TestFileCommand() {
            registerArguments(arg);
        }

        public void execute() throws Exception {
            getOutput().getPrintWriter().print(arg.getValue());
        }
    }

    @Test
    public void testFileArgument() throws Exception {
        TestShell shell = new TestShell();
        shell.addAlias("command", "org.jnode.test.shell.syntax.ArgumentTypesTest$TestFileCommand");
        shell.addSyntax("command", new ArgumentSyntax("arg1"));
        CommandLine cl = new CommandLine(new Token("command"), new Token[] {new Token("F1")}, null);
        CommandInfo cmdInfo = cl.parseCommandLine(shell);
        Command cmd = cmdInfo.createCommandInstance();
        Assert.assertEquals("F1", cmd.getArgumentBundle().getArgument("arg1").getValue().toString());

        try {
            cl = new CommandLine(new Token("command"), new Token[] {new Token("")}, null);
            cl.parseCommandLine(shell);
            Assert.fail("parse didn't fail");
        } catch (CommandSyntaxException ex) {
            // expected
        }
    }
}
