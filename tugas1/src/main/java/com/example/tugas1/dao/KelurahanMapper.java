package com.example.tugas1.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tugas1.model.KelurahanModel;

@Mapper
public interface KelurahanMapper {
	
	@Select("select id, nama_kelurahan, kode_kelurahan, id_kecamatan, kode_pos from kelurahan where nama_kelurahan = #{nama}")
    KelurahanModel selectKelurahan (@Param("nama") String nama);
}

