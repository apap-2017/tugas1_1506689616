package com.example.tugas1.service;

import java.util.List;

import com.example.tugas1.dao.DomisiliMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.tugas1.model.KotaModel;
import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.PendudukModel;

@Service
public class DomisiliServiceDatabase implements DomisiliService {
	
	@Autowired
	private DomisiliMapper domisiliMapper;
	
    @Override
    public List<KotaModel> selectAllKota() {
    	List<KotaModel> allKota = domisiliMapper.selectAllKota();
    	
    	return allKota;
    }
    
    @Override
    public List<KecamatanModel> selectAllKecamatan(String id_kota) {
    	List<KecamatanModel> allKecamatan = domisiliMapper.selectAllKecamatan(id_kota);
    	
    	return allKecamatan;
    }
    
    @Override
    public String selectNamaKota(String id_kota) {
    	String namaKota = domisiliMapper.selectNamaKota(id_kota);
    	
    	return namaKota;
    }
    
    @Override
    public List<KelurahanModel> selectAllKelurahan(String id_kecamatan) {
    	List<KelurahanModel> allKelurahan = domisiliMapper.selectAllKelurahan(id_kecamatan);
    	
    	return allKelurahan;
    }
    
    @Override
    public String selectNamaKecamatan(String id_kecamatan) {
    	String namaKecamatan = domisiliMapper.selectNamaKecamatan(id_kecamatan);
    	
    	return namaKecamatan;
    }
    
    @Override
    public List<PendudukModel> selectAllPenduduk(String id_kelurahan) {
    	List<PendudukModel> allPenduduk = domisiliMapper.selectAllPenduduk(id_kelurahan);
    	
    	return allPenduduk;
    }
}
