package com.example.tugas1.service;

import com.example.tugas1.model.KeluargaModel;

public interface KeluargaService {
	
	KeluargaModel selectKeluarga(String nkk);
	
	String masukanKeluarga(KeluargaModel keluarga);
	
	String updateKeluarga(KeluargaModel keluarga, String nkkLama);
}
