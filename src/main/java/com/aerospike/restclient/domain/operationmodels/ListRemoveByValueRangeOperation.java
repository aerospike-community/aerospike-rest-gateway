/*
 * Copyright 2022 Aerospike, Inc.
 *
 * Portions may be licensed to Aerospike, Inc. under one or more contributor
 * license agreements WHICH ARE COMPATIBLE WITH THE APACHE LICENSE, VERSION 2.0.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.aerospike.restclient.domain.operationmodels;

import com.aerospike.client.Value;
import com.aerospike.restclient.util.AerospikeAPIConstants;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Remove all items from the list with values between `valueBegin` and `valueEnd`. If `valueBegin` is omitted all items with a value less than `valueEnd` will be removed. If `valueEnd` is omitted all items with a value greater than `valueBegin` will be removed. Requires Aerospike Server `3.16.0.1` or later.",
        externalDocs = @ExternalDocumentation(url = "https://javadoc.io/doc/com.aerospike/aerospike-client/" + AerospikeAPIConstants.AS_CLIENT_VERSION + "/com/aerospike/client/cdt/ListOperation.html")
)
public class ListRemoveByValueRangeOperation extends ListOperation {

    @Schema(
            description = "The type of operation. It is always " + OperationTypes.LIST_REMOVE_BY_VALUE_RANGE,
            required = true,
            allowableValues = OperationTypes.LIST_REMOVE_BY_VALUE_RANGE
    )
    final public static String type = OperationTypes.LIST_REMOVE_BY_VALUE_RANGE;

    @Schema(required = true)
    private final ListReturnType listReturnType;

    private boolean inverted;

    private Object valueBegin;

    private Object valueEnd;

    @JsonCreator
    public ListRemoveByValueRangeOperation(@JsonProperty(value = "binName", required = true) String binName,
                                           @JsonProperty(
                                                   value = "listReturnType", required = true
                                           ) ListReturnType listReturnType) {
        super(binName);
        this.listReturnType = listReturnType;
    }

    public Object getValueBegin() {
        return valueBegin;
    }

    public void setValueBegin(Object valueBegin) {
        this.valueBegin = valueBegin;
    }

    public Object getValueEnd() {
        return valueEnd;
    }

    public void setValueEnd(Object valueEnd) {
        this.valueEnd = valueEnd;
    }

    public boolean isInverted() {
        return inverted;
    }

    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

    @Override
    public com.aerospike.client.Operation toOperation() {
        com.aerospike.client.cdt.CTX[] asCTX = getASCTX();
        Value begin = null;
        Value end = null;

        if (valueBegin != null) {
            begin = Value.get(valueBegin);
        }

        if (valueEnd != null) {
            end = Value.get(valueEnd);
        }

        return com.aerospike.client.cdt.ListOperation.removeByValueRange(binName, begin, end, listReturnType.toListReturnType(inverted), asCTX);
    }
}
