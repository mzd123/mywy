package com.mzd.mywy.dao;

import com.mzd.mywy.ormbean.Wylog;
import com.mzd.mywy.ormbean.WylogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WylogMapper {
    long countByExample(WylogExample example);

    int deleteByExample(WylogExample example);

    int insert(Wylog record);

    int insertSelective(Wylog record);

    List<Wylog> selectByExample(WylogExample example);

    int updateByExampleSelective(@Param("record") Wylog record, @Param("example") WylogExample example);

    int updateByExample(@Param("record") Wylog record, @Param("example") WylogExample example);
}