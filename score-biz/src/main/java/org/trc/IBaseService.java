package org.trc;

import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by george on 2017/3/23.
 */
@Service
public interface IBaseService<T,PK> {

    int insert(T record);

    int insertList(List<T> records);

    int insertSelective(T record);

    int deleteByPrimaryKey(PK key);

    int updateByPrimaryKey(T record);

    int updateByPrimaryKeySelective(T record);

    int updateByExample(T t, Example example);

    T selectOne(T record);

    List<T> select(T record);

    List<T> selectByExample(Object example);

}
