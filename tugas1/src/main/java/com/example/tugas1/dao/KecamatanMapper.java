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

import com.example.tugas1.model.KecamatanModel;

@Mapper
public interface KecamatanMapper {
	@Select("select id, id_kota, nama_kecamatan, kode_kecamatan from kecamatan where nama_kecamatan = #{nama}")
    KecamatanModel selectKecamatan (@Param("nama") String nama);
	
}
