package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.tugas1.model.KotaModel;
import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.PendudukModel;

@Mapper
public interface DomisiliMapper {
	
	@Select("select nama_kota, id, kode_kota from kota")
	List<KotaModel> selectAllKota();
	
	@Select("select id, nama_kecamatan, id_kota, kode_kecamatan from kecamatan where id_kota=#{id_kota}")
	List<KecamatanModel> selectAllKecamatan(String id_kota);
	
	@Select("select nama_kota from kota where id=#{id_kota}")
	String selectNamaKota(String id_kota);
	
	@Select("select id, nama_kelurahan, id_kecamatan, kode_kelurahan, kode_pos from kelurahan where id_kecamatan=#{id_kecamatan}")
	List<KelurahanModel> selectAllKelurahan(String id_kecamatan);
	
	@Select("select nama_kecamatan from kecamatan where id=#{id_kecamatan}")
	String selectNamaKecamatan(String id_kecamatan);
	
	@Select("select nik, nama, jenis_kelamin from penduduk p, keluarga k where p.id_keluarga = k.id AND k.id_kelurahan=#{id_kelurahan}")
	List<PendudukModel> selectAllPenduduk(String id_kelurahan);
}
