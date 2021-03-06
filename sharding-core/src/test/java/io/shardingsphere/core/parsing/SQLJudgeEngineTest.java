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

package io.shardingsphere.core.parsing;

import io.shardingsphere.core.parsing.parser.dialect.mysql.statement.DescribeStatement;
import io.shardingsphere.core.parsing.parser.dialect.mysql.statement.ShowColumnsStatement;
import io.shardingsphere.core.parsing.parser.dialect.mysql.statement.ShowDatabasesStatement;
import io.shardingsphere.core.parsing.parser.dialect.mysql.statement.ShowOtherStatement;
import io.shardingsphere.core.parsing.parser.dialect.mysql.statement.ShowTablesStatement;
import io.shardingsphere.core.parsing.parser.dialect.mysql.statement.UseStatement;
import io.shardingsphere.core.parsing.parser.exception.SQLParsingException;
import io.shardingsphere.core.parsing.parser.sql.dml.DMLStatement;
import io.shardingsphere.core.parsing.parser.sql.dml.insert.InsertStatement;
import io.shardingsphere.core.parsing.parser.sql.dql.DQLStatement;
import io.shardingsphere.core.parsing.parser.sql.tcl.TCLStatement;
import io.shardingsphere.core.parsing.parser.dialect.mysql.statement.DescribeStatement;
import io.shardingsphere.core.parsing.parser.dialect.mysql.statement.ShowColumnsStatement;
import io.shardingsphere.core.parsing.parser.dialect.mysql.statement.ShowDatabasesStatement;
import io.shardingsphere.core.parsing.parser.dialect.mysql.statement.ShowOtherStatement;
import io.shardingsphere.core.parsing.parser.dialect.mysql.statement.ShowTablesStatement;
import io.shardingsphere.core.parsing.parser.dialect.mysql.statement.UseStatement;
import io.shardingsphere.core.parsing.parser.exception.SQLParsingException;
import io.shardingsphere.core.parsing.parser.sql.dml.DMLStatement;
import io.shardingsphere.core.parsing.parser.sql.dml.insert.InsertStatement;
import io.shardingsphere.core.parsing.parser.sql.dql.DQLStatement;
import io.shardingsphere.core.parsing.parser.sql.tcl.TCLStatement;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public final class SQLJudgeEngineTest {
    
    @Test
    public void assertJudgeForSelect() {
        assertThat(new SQLJudgeEngine(" /*COMMENT*/  \t \n  \r \fsElecT\t\n  * from table  ").judge(), instanceOf(DQLStatement.class));
    }
    
    @Test
    public void assertJudgeForInsert() {
        assertThat(new SQLJudgeEngine(" - - COMMENT  \t \n  \r \finsert\t\n  into table  ").judge(), instanceOf(InsertStatement.class));
    }
    
    @Test
    public void assertJudgeForUpdate() {
        assertThat(new SQLJudgeEngine(" /*+ HINT SELECT * FROM TT*/  \t \n  \r \fuPdAte\t\n  table  ").judge(), instanceOf(DMLStatement.class));
    }
    
    @Test
    public void assertJudgeForDelete() {
        assertThat(new SQLJudgeEngine(" /*+ HINT SELECT * FROM TT*/  \t \n  \r \fdelete\t\n  table  ").judge(), instanceOf(DMLStatement.class));
    }
    
    @Test
    public void assertJudgeForSet() {
        assertThat(new SQLJudgeEngine(" /*+ HINT SELECT * FROM TT*/  \t \n  \r \fset\t\n  autocommit  ").judge(), instanceOf(TCLStatement.class));
    }
    
    @Test
    public void assertJudgeForCommit() {
        assertThat(new SQLJudgeEngine(" /*+ HINT SELECT * FROM TT*/  \t \n  \r \fcommit  ").judge(), instanceOf(TCLStatement.class));
    }
    
    @Test
    public void assertJudgeForRollback() {
        assertThat(new SQLJudgeEngine(" /*+ HINT SELECT * FROM TT*/  \t \n  \r \frollback  ").judge(), instanceOf(TCLStatement.class));
    }
    
    @Test
    public void assertJudgeForSavePoint() {
        assertThat(new SQLJudgeEngine(" /*+ HINT SELECT * FROM TT*/  \t \n  \r \fSavePoint  ").judge(), instanceOf(TCLStatement.class));
    }
    
    @Test
    public void assertJudgeForBegin() {
        assertThat(new SQLJudgeEngine(" /*+ HINT SELECT * FROM TT*/  \t \n  \r \fbegin  ").judge(), instanceOf(TCLStatement.class));
    }
    
    @Test
    public void assertJudgeForUse() {
        assertThat(new SQLJudgeEngine(" /*+ HINT SELECT * FROM TT*/  \t \n  \r \fuse sharding_db  ").judge(), instanceOf(UseStatement.class));
    }
    
    @Test
    public void assertJudgeForDescribe() {
        assertThat(new SQLJudgeEngine(" /*+ HINT SELECT * FROM TT*/  \t \n  \r \fdescribe t_order  ").judge(), instanceOf(DescribeStatement.class));
    }
    
    @Test
    public void assertJudgeForDesc() {
        assertThat(new SQLJudgeEngine(" /*+ HINT SELECT * FROM TT*/  \t \n  \r \fdesc t_order  ").judge(), instanceOf(DescribeStatement.class));
    }
    
    @Test
    public void assertJudgeForShowDatabases() {
        assertThat(new SQLJudgeEngine(" /*+ HINT SELECT * FROM TT*/  \t \n  \r \fshow databases  ").judge(), instanceOf(ShowDatabasesStatement.class));
    }
    
    @Test
    public void assertJudgeForShowTables() {
        assertThat(new SQLJudgeEngine(" /*+ HINT SELECT * FROM TT*/  \t \n  \r \fshow tables  ").judge(), instanceOf(ShowTablesStatement.class));
    }
    
    @Test
    public void assertJudgeForShowColumns() {
        assertThat(new SQLJudgeEngine(" /*+ HINT SELECT * FROM TT*/  \t \n  \r \fshow columns t_order ").judge(), instanceOf(ShowColumnsStatement.class));
    }
    
    @Test
    public void assertJudgeForShowOthers() {
        assertThat(new SQLJudgeEngine(" /*+ HINT SELECT * FROM TT*/  \t \n  \r \fshow session ").judge(), instanceOf(ShowOtherStatement.class));
    }
    
    @Test(expected = SQLParsingException.class)
    public void assertJudgeForInvalidSQL() {
        new SQLJudgeEngine("int i = 0").judge();
    }
}
