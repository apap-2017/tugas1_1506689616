package com.example.tugas1.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.KeluargaMapper;
import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.dao.KelurahanMapper;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.dao.KecamatanMapper;
import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.dao.KotaMapper;
import com.example.tugas1.model.KotaModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService {
	@Autowired
    private KeluargaMapper keluargaMapper;
	@Autowired
    private KelurahanMapper kelurahanMapper;
	@Autowired
    private KecamatanMapper kecamatanMapper;
	@Autowired
    private KotaMapper kotaMapper;


    @Override
    public KeluargaModel selectKeluarga (String nkk)
    {
    	return keluargaMapper.selectKeluarga (nkk);
    }
    
    @Override
    public String masukanKeluarga(KeluargaModel keluarga) 
    {
    	String hasil = "";
    	KelurahanModel kelurahan = kelurahanMapper.selectKelurahan(keluarga.getNama_kelurahan());
    	KecamatanModel kecamatan = kecamatanMapper.selectKecamatan(keluarga.getNama_kecamatan());
    	KotaModel kota = kotaMapper.selectKota(keluarga.getNama_kota());
    	
    	if(kelurahan == null || kecamatan == null || kota == null) {
    		hasil = "notfound";
    	} else if(Integer.parseInt(kelurahan.getId_kecamatan()) != kecamatan.getId() || Integer.parseInt(kecamatan.getId_kota()) != kota.getId()){
    		hasil = "notmatch";
    	} else {
    		keluarga.setId_kelurahan(Integer.toString(kelurahan.getId()));
    		keluarga.setIs_tidak_berlaku(0);
    		String kodeKecamatan = kecamatan.getKode_kecamatan();
    		kodeKecamatan = kodeKecamatan.substring(0,kodeKecamatan.length()-1);
    		
    		LocalDateTime today = LocalDateTime.now();
        	
        	String year = Integer.toString(today.getYear()).substring(2,4);
        	String month = Integer.toString(today.getMonthValue());
        	String day = Integer.toString(today.getDayOfMonth());
        	
        	String digitAwal = kodeKecamatan + day + month + year;
        	
        	int count = 1;
        	String nkk = digitAwal + "0001";
        	KeluargaModel cekNkk = keluargaMapper.selectKeluarga(nkk);
        	
        	while(cekNkk != null) {
            	String digitAkhirNkk = cekNkk.getNomor_kk().substring(12,16);
            	int cekDigitAkhir = Integer.parseInt(digitAkhirNkk) + count;
            	String digitAkhir = "";
            	if(cekDigitAkhir < 10) {
            		digitAkhir = "000" + cekDigitAkhir;
            	} else if(cekDigitAkhir >= 10 && cekDigitAkhir < 100) {
            		digitAkhir = "00" + cekDigitAkhir;	
            	} else if(cekDigitAkhir >= 100 && cekDigitAkhir < 1000) {
            		digitAkhir = "0" + cekDigitAkhir;
            	}
            	
            	nkk = digitAwal + digitAkhir;
            	cekNkk = keluargaMapper.selectKeluarga(nkk);
        	}
        	
        	keluarga.setNomor_kk(nkk);
        	hasil = nkk;
        	keluargaMapper.masukanKeluarga(keluarga);
    	}
    
    	return hasil;
    }
    
    @Override
    public String updateKeluarga(KeluargaModel keluarga, String nkkLama) {
    	String hasil = "";
    	KelurahanModel kelurahan = kelurahanMapper.selectKelurahan(keluarga.getNama_kelurahan());
    	KecamatanModel kecamatan = kecamatanMapper.selectKecamatan(keluarga.getNama_kecamatan());
    	KotaModel kota = kotaMapper.selectKota(keluarga.getNama_kota());
    	
    	if(kelurahan == null || kecamatan == null || kota == null) {
    		hasil = "notfoundkeluarga";
    	} else if(Integer.parseInt(kelurahan.getId_kecamatan()) != kecamatan.getId() || Integer.parseInt(kecamatan.getId_kota()) != kota.getId()){
    		hasil = "notmatchkeluarga";
    	} else {
    		KeluargaModel dataLama = keluargaMapper.selectKeluarga(nkkLama);
    		keluarga.setId_kelurahan(Integer.toString(kelurahan.getId()));
    		keluarga.setIs_tidak_berlaku(0);
    		String kodeKecamatan = kecamatan.getKode_kecamatan();
    		kodeKecamatan = kodeKecamatan.substring(0,kodeKecamatan.length()-1);
    		
	    	LocalDateTime today = LocalDateTime.now();
	    	
	    	String year = Integer.toString(today.getYear()).substring(2,4);
	    	String month = Integer.toString(today.getMonthValue());
	    	String day = Integer.toString(today.getDayOfMonth());
	    	
	    	String digitAwal = kodeKecamatan + day + month + year;
	    		
	    	if(dataLama.getNomor_kk().substring(0,12).equals(digitAwal)) {
	    		keluarga.setNomor_kk(nkkLama);
	    	} else {
	    		int count = 1;
	        	String nkk = digitAwal + "0001";
	        	KeluargaModel cekNkk = keluargaMapper.selectKeluarga(nkk);
	        	
	        	while(cekNkk != null) {
	            	String digitAkhirNkk = cekNkk.getNomor_kk().substring(12,16);
	            	int cekDigitAkhir = Integer.parseInt(digitAkhirNkk) + count;
	            	String digitAkhir = "";
	            	if(cekDigitAkhir < 10) {
	            		digitAkhir = "000" + cekDigitAkhir;
	            	} else if(cekDigitAkhir >= 10 && cekDigitAkhir < 100) {
	            		digitAkhir = "00" + cekDigitAkhir;	
	            	} else if(cekDigitAkhir >= 100 && cekDigitAkhir < 1000) {
	            		digitAkhir = "0" + cekDigitAkhir;
	            	}
	            	
	            	nkk = digitAwal + digitAkhir;
	            	cekNkk = keluargaMapper.selectKeluarga(nkk);
	        	}
        	
	        	keluarga.setNomor_kk(nkk);
	    	}
	    	
	    	keluargaMapper.updateKeluarga(keluarga, nkkLama);
    	}
    	
    	return hasil;
    }
}
