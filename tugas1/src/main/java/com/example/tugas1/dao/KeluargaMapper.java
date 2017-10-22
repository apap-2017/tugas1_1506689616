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

import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.PendudukModel;

@Mapper
public interface KeluargaMapper {
	@Select("select k.nomor_kk, k.alamat, k.rt, k.rw, kl.nama_kelurahan, kc.nama_kecamatan, kt.nama_kota from keluarga k, kelurahan kl, kecamatan kc, kota kt where k.nomor_kk = #{nkk} AND k.id_kelurahan = kl.id AND kl.id_kecamatan = kc.id AND kc.id_kota = kt.id")
    @Results(value = {
    		@Result(property="nomor_kk", column="nomor_kk"),
    		@Result(property="alamat", column="alamat"),
    		@Result(property="rt", column="rt"),
    		@Result(property="rw", column="rw"),
    		@Result(property="nama_kelurahan", column="nama_kelurahan"),
    		@Result(property="nama_kecamatan", column="nama_kecamatan"),
    		@Result(property="nama_kota", column="nama_kota"),
    		@Result(property="anggotaKeluarga", column="nomor_kk",
    		javaType = List.class,
    		many=@Many(select="selectAnggotaKeluarga"))
    		})
	KeluargaModel selectKeluarga (@Param("nkk") String nkk);
	
	@Select("SELECT p.nama, p.nik, p.jenis_kelamin, p.tempat_lahir, p.tanggal_lahir, p.agama, p.pekerjaan, p.status_perkawinan, p.status_dalam_keluarga, p.is_wni " +
    		"FROM penduduk p join keluarga k " +
    		"ON p.id_keluarga = k.id " +
    		"WHERE k.nomor_kk = #{nkk}")
	List<PendudukModel> selectAnggotaKeluarga(@Param("nkk") String nkk);
	
	@Insert("insert into keluarga (nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku) " +
			" values (#{nomor_kk}, #{alamat}, #{rt}, #{rw}, #{id_kelurahan}, #{is_tidak_berlaku})") 
	void masukanKeluarga(KeluargaModel keluarga);
	
	@Update("update keluarga set nomor_kk=#{keluarga.nomor_kk}, alamat=#{keluarga.alamat}, rt=#{keluarga.rt}, rw=#{keluarga.rw}, id_kelurahan=#{keluarga.id_kelurahan} " +
			" where nomor_kk = #{nkk}")
	void updateKeluarga(@Param("keluarga")KeluargaModel keluarga, @Param("nkk") String nkk);
	
	@Update("update keluarga set is_tidak_berlaku = '1' where id = #{id}")
	void updateKK(int id);
	
	@Select("select id_kelurahan, nomor_kk from keluarga where id=#{id}")
	KeluargaModel selectKeluargaById(int id);
}
