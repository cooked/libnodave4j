/*
 * ============================================================================
 * GNU General Public License
 * ============================================================================
 *
 * Copyright (C) 2006-2011 Serotonin Software Technologies Inc. http://serotoninsoftware.com
 * @author Matthew Lohbihler
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
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package sc.siemens.exception;

import sc.siemens.msg.S7Request;
import sc.siemens.msg.S7Response;

public class ErrorResponseException extends Exception {
    private static final long serialVersionUID = -1;

    private final S7Request originalRequest;
    private final S7Response errorResponse;

    public ErrorResponseException(S7Request originalRequest, S7Response errorResponse) {
        this.originalRequest = originalRequest;
        this.errorResponse = errorResponse;
    }

    public S7Response getErrorResponse() {
        return errorResponse;
    }

    public S7Request getOriginalRequest() {
        return originalRequest;
    }

    @Override
    public String getMessage() {
        return errorResponse.getExceptionMessage();
    }
}
