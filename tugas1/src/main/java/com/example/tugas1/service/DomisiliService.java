package com.example.tugas1.service;

import java.util.List;

import com.example.tugas1.model.KotaModel;
import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.PendudukModel;

@SuppressWarnings("unused")
public interface DomisiliService {
	
	List<KotaModel> selectAllKota();
	
	List<KecamatanModel> selectAllKecamatan(String id_kota);
	
	String selectNamaKota(String id_kota);
	
	List<KelurahanModel> selectAllKelurahan(String id_kecamatan);
	
	String selectNamaKecamatan(String id_kecamatan);
	
	List<PendudukModel> selectAllPenduduk(String id_kelurahan);
}
