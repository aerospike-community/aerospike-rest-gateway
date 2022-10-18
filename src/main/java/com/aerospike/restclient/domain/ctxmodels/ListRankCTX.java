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
package com.aerospike.restclient.domain.ctxmodels;

import com.aerospike.restclient.util.AerospikeAPIConstants;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Lookup list by rank",
        externalDocs = @ExternalDocumentation(url = "https://javadoc.io/doc/com.aerospike/aerospike-client/" + AerospikeAPIConstants.AS_CLIENT_VERSION + "/com/aerospike/client/cdt/CTX.html")
)
public class ListRankCTX extends CTX {
    @Schema(
            description = "The type of context this object represents. It is always " + AerospikeAPIConstants.CTX.LIST_RANK,
            allowableValues = AerospikeAPIConstants.CTX.LIST_RANK,
            required = true
    )
    public final String type = AerospikeAPIConstants.CTX.LIST_RANK;

    @Schema(
            description = """
                    * 0 = smallest value
                    * N = Nth smallest value
                    * -1 = largest value""", required = true
    )
    public Integer rank;

    ListRankCTX() {
    }

    public ListRankCTX(int rank) {
        this.rank = rank;
    }

    @Override
    public com.aerospike.client.cdt.CTX toCTX() {
        return com.aerospike.client.cdt.CTX.listRank(rank);
    }
}


