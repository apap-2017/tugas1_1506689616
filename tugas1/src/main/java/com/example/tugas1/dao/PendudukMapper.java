package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tugas1.model.PendudukModel;
import com.example.tugas1.model.KeluargaModel;

@Mapper
public interface PendudukMapper {
	
	@Select("select p.id, p.nik, p.nama, p.tempat_lahir, p.tanggal_lahir, p.jenis_kelamin," + 
	" p.is_wni, p.id_keluarga, p.agama, p.pekerjaan, p.status_perkawinan, p.status_dalam_keluarga, " +
	" p.golongan_darah, p.is_wafat, k.alamat, k.rt, k.rw, kl.nama_kelurahan, kc.nama_kecamatan, " + 
	" kt.nama_kota from penduduk p, keluarga k, kelurahan kl, kecamatan kc, kota kt " +
	" where p.nik = #{nik} AND p.id_keluarga = k.id AND k.id_kelurahan = kl.id AND " +
	" kl.id_kecamatan = kc.id AND kc.id_kota = kt.id")
    PendudukModel selectPenduduk (@Param("nik") String nik);

	@Insert("insert into penduduk (nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, " +
	" is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, " + 
	" golongan_darah, is_wafat) values (#{nik}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin}, " + 
	" #{is_wni}, #{id_keluarga}, #{agama}, #{pekerjaan}, #{status_perkawinan}, #{status_dalam_keluarga}, " + 
	" #{golongan_darah}, #{is_wafat})") 
	void masukanPenduduk(PendudukModel penduduk);
	
	@Select("select kc.kode_kecamatan from keluarga k, kelurahan kl, kecamatan kc where k.id = #{id_keluarga} AND k.id_kelurahan = kl.id AND kl.id_kecamatan = kc.id")
	String lihatKodeKecamatan(@Param("id_keluarga") int id_keluarga);
	
	@Update("update penduduk set nik = #{penduduk.nik}, nama = #{penduduk.nama}, tempat_lahir = #{penduduk.tempat_lahir}, tanggal_lahir = #{penduduk.tanggal_lahir}, jenis_kelamin = #{penduduk.jenis_kelamin}," + 
			"is_wni = #{penduduk.is_wni}, id_keluarga = #{penduduk.id_keluarga}, agama = #{penduduk.agama}, pekerjaan = #{penduduk.pekerjaan}, status_perkawinan = #{penduduk.status_perkawinan}, status_dalam_keluarga = #{penduduk.status_dalam_keluarga}," + 
			"golongan_darah = #{penduduk.golongan_darah}, is_wafat = #{penduduk.is_wafat} where nik = #{nikLama}")
	void updatePenduduk(@Param("penduduk") PendudukModel penduduk, @Param("nikLama") String nikLama);
	
	@Update("update penduduk set is_wafat = '1' where nik = #{nik}")
	void setWafat(String nik);
	
	@Select("select is_wafat from penduduk where id_keluarga = #{id} AND is_wafat='0'")
	List<String> selectIdKeluarga(int id);
}
