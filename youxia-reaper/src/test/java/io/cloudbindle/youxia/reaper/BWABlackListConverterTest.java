/*
 * Copyright (C) 2014 CloudBindle
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.cloudbindle.youxia.reaper;

import org.junit.Test;

/**
 *
 * @author dyuen
 */
public class BWABlackListConverterTest {

    /**
     * Test of main method, of class BWABlackListConverter.
     */
    @Test(expected = RuntimeException.class)
    public void testMain() throws Exception {
        String[] args = { "--help" };
        BWABlackListConverter.main(args);
    }

}
