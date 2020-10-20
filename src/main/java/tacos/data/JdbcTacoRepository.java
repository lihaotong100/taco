package tacos.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import tacos.Ingredient;
import tacos.Taco;

@Repository
public class JdbcTacoRepository implements TacoRepository{
	private JdbcTemplate jdbc;
	
	@Autowired
	public JdbcTacoRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public Taco save(Taco taco) {
		long tacoId = saveTacoInfo(taco);
		taco.setId(tacoId);
		for(Ingredient ingerIngredient : taco.getIngredients()) {
			
		}
		return null;
	}
	
	private long saveTacoInfo(Taco taco) {
		taco.setCreateDate(new Date());
		PreparedStatementCreator psc = new PreparedStatementCreatorFactory("insert into Taco (name,createAt) values (?,?)", 
				Types.VARCHAR,Types.TIMESTAMP).newPreparedStatementCreator(Arrays.asList(taco.getName(),new Timestamp(taco.getCreateDate().getTime())));

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(psc, keyHolder);
		return keyHolder.getKey().longValue();
	}
	
}
