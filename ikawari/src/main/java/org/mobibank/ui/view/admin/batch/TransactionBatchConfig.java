package org.mobibank.ui.view.admin.batch;

import javax.sql.DataSource;

import org.mobibank.backend.data.entity.Transaction;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

@Configuration
@EnableBatchProcessing
public class TransactionBatchConfig {
	
	private static final String QUERY_FIND_TRANSACTIONS =
            "SELECT " +
                "* " +
            "FROM TRANSACTION " +
            "ORDER BY id DESC";
	
	@Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;
    
    @Bean
    public JdbcCursorItemReader<Transaction> transactionItemReader(){
    	JdbcCursorItemReader<Transaction> databaseReader = new JdbcCursorItemReader<>();
    	 
        databaseReader.setDataSource(dataSource);
        databaseReader.setSql(QUERY_FIND_TRANSACTIONS);
        databaseReader.setRowMapper(new BeanPropertyRowMapper<>(Transaction.class));
        
        return databaseReader;
    }

}
