/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingsphere.core.parsing.parser.dialect.postgresql.clause;

import io.shardingsphere.core.constant.OrderDirection;
import io.shardingsphere.core.parsing.lexer.LexerEngine;
import io.shardingsphere.core.parsing.parser.clause.OrderByClauseParser;
import io.shardingsphere.core.constant.OrderDirection;

/**
 * Order by clause parser for PostgreSQL.
 *
 * @author zhangliang
 */
public final class PostgreSQLOrderByClauseParser extends OrderByClauseParser {
    
    public PostgreSQLOrderByClauseParser(final LexerEngine lexerEngine) {
        super(lexerEngine);
    }
    
    @Override
    protected OrderDirection getNullOrderDirection() {
        return OrderDirection.DESC;
    }
}
