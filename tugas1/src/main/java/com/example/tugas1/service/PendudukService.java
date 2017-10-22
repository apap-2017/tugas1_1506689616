package com.example.tugas1.service;

import java.util.List;
import com.example.tugas1.model.PendudukModel;
import com.example.tugas1.model.KotaModel;

public interface PendudukService {
	
	PendudukModel selectPenduduk(String nik, String flag);
	
	String masukanPenduduk(PendudukModel penduduk);
	
	void updatePenduduk(PendudukModel penduduk, String nikLama);
	
	void setWafat(String nik);
}
