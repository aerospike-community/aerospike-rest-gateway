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
        description = "Return all map items with value in the range [`valueBegin`, `valueEnd`). If `valueBegin` is omitted, all map items with a value less than `valueEnd` will be returned. If `valueEnd` is omitted, all map items with a value greater than or equal to `valueBegin` will be returned.",
        externalDocs = @ExternalDocumentation(url = "https://javadoc.io/doc/com.aerospike/aerospike-client/" + AerospikeAPIConstants.AS_CLIENT_VERSION + "/com/aerospike/client/cdt/MapOperation.html")
)
public class MapGetByValueRangeOperation extends MapOperation {

    @Schema(
            description = "The type of operation. It is always " + OperationTypes.MAP_GET_BY_VALUE_RANGE,
            required = true,
            allowableValues = OperationTypes.MAP_GET_BY_VALUE_RANGE
    )
    final public static String type = OperationTypes.MAP_GET_BY_VALUE_RANGE;

    @Schema(required = true)
    private final MapReturnType mapReturnType;

    private boolean inverted;

    private Object valueBegin;

    private Object valueEnd;

    @JsonCreator
    public MapGetByValueRangeOperation(@JsonProperty(value = "binName", required = true) String binName, @JsonProperty(
            value = "mapReturnType", required = true
    ) MapReturnType mapReturnType) {
        super(binName);
        this.mapReturnType = mapReturnType;
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

        return com.aerospike.client.cdt.MapOperation.getByValueRange(binName, begin, end,
                mapReturnType.toMapReturnType(inverted), asCTX);
    }
}
