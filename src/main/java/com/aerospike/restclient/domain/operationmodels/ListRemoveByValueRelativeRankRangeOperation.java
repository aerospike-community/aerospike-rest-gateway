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
        description = "TODO",
        externalDocs = @ExternalDocumentation(url = "https://javadoc.io/doc/com.aerospike/aerospike-client/" + AerospikeAPIConstants.AS_CLIENT_VERSION + "/com/aerospike/client/cdt/MapOperation.html")
)
public class ListRemoveByValueRelativeRankRangeOperation extends ListOperation {

    @Schema(
            description = "The type of operation. It is always " + OperationTypes.LIST_REMOVE_BY_VALUE_RELATIVE_RANK_RANGE,
            required = true,
            allowableValues = OperationTypes.LIST_REMOVE_BY_VALUE_RELATIVE_RANK_RANGE
    )
    final public static String type = OperationTypes.LIST_REMOVE_BY_VALUE_RELATIVE_RANK_RANGE;

    @Schema(required = true)
    private final int rank;

    @Schema(required = true)
    private final Object value;

    @Schema(required = true)
    private final ListReturnType listReturnType;

    private boolean inverted;

    private Integer count;

    @JsonCreator
    public ListRemoveByValueRelativeRankRangeOperation(@JsonProperty("binName") String binName,
                                                       @JsonProperty("rank") int rank,
                                                       @JsonProperty("value") Object value,
                                                       @JsonProperty("listReturnType") ListReturnType listReturnType) {
        super(binName);
        this.rank = rank;
        this.value = value;
        this.listReturnType = listReturnType;
        inverted = false;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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
        int intMapReturnType = listReturnType.toListReturnType(inverted);

        if (count == null) {
            return com.aerospike.client.cdt.ListOperation.removeByValueRelativeRankRange(binName, Value.get(value),
                    rank, intMapReturnType, asCTX);
        }

        return com.aerospike.client.cdt.ListOperation.removeByValueRelativeRankRange(binName, Value.get(value), rank,
                count, intMapReturnType, asCTX);
    }
}
