package rpg.server.gen.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import rpg.server.gen.db.model.DemoHuman;
import rpg.server.gen.db.model.DemoHumanExample;

public interface DemoHumanMapper {
    int countByExample(DemoHumanExample example);
	
    int deleteByExample(DemoHumanExample example);
	
    int deleteByPrimaryKey(@Param("id") Long id);
	
    int insert(DemoHuman record);

    int insertSelective(DemoHuman record);
	
    List<DemoHuman> selectByExample(DemoHumanExample example);
	
    DemoHuman selectByPrimaryKey(@Param("id") Long id);
	
    int updateByExampleSelective(@Param("record") DemoHuman record, @Param("example") DemoHumanExample example);

    int updateByExample(@Param("record") DemoHuman record, @Param("example") DemoHumanExample example);
	
    int updateByPrimaryKeySelective(DemoHuman record);

    int updateByPrimaryKey(DemoHuman record);
}