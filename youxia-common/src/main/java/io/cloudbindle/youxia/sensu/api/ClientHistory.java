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

package io.cloudbindle.youxia.sensu.api;

/**
 * 
 * @author dyuen
 */
public class ClientHistory {
    private String check;
    private int[] history;
    private Long lastExecution;
    private int lastStatus;

    /**
     * @return the check
     */
    public String getCheck() {
        return check;
    }

    /**
     * @param check the check to set
     */
    public void setCheck(String check) {
        this.check = check;
    }

    /**
     * @return the history
     */
    public int[] getHistory() {
        return history;
    }

    /**
     * @param history the history to set
     */
    public void setHistory(int[] history) {
        this.history = history;
    }

    /**
     * @return the lastExecution
     */
    public Long getLastExecution() {
        return lastExecution;
    }

    /**
     * @param lastExecution the lastExecution to set
     */
    public void setLastExecution(Long lastExecution) {
        this.lastExecution = lastExecution;
    }

    /**
     * @return the lastStatus
     */
    public int getLastStatus() {
        return lastStatus;
    }

    /**
     * @param lastStatus the lastStatus to set
     */
    public void setLastStatus(int lastStatus) {
        this.lastStatus = lastStatus;
    }

}
