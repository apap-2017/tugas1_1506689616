package com.example.tugas1.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.PendudukMapper;
import com.example.tugas1.model.PendudukModel;
import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.dao.KeluargaMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService {
	@Autowired
    private PendudukMapper pendudukMapper;
	@Autowired
    private KeluargaMapper keluargaMapper;


    @Override
    public PendudukModel selectPenduduk (String nik, String flag)
    {
    	PendudukModel penduduk = pendudukMapper.selectPenduduk(nik);
    	if(penduduk != null && flag == null) {
    		String tanggalLahir = penduduk.getTanggal_lahir();
        	String[] extract = tanggalLahir.split("-");
        	String hari = extract[2];
        	String bulan = extract[1];
        	
        	if(bulan.equals("01")) {
        		bulan = "Januari";
        	}
        	if(bulan.equals("02")) {
        		bulan = "Februari";
        	}
        	if(bulan.equals("03")) {
        		bulan = "Maret";
        	}
        	if(bulan.equals("04")) {
        		bulan = "April";
        	}
        	if(bulan.equals("05")) {
        		bulan = "Mei";
        	}
        	if(bulan.equals("06")) {
        		bulan = "Juni";
        	}
        	if(bulan.equals("07")) {
        		bulan = "Juli";
        	}
        	if(bulan.equals("08")) {
        		bulan = "Agustus";
        	}
        	if(bulan.equals("09")) {
        		bulan = "September";
        	}
        	if(bulan.equals("10")) {
        		bulan = "Oktober";
        	}
        	if(bulan.equals("11")) {
        		bulan = "November";
        	}
        	if(bulan.equals("12")) {
        		bulan = "Desember";
        	}
        	
        	String tahun = extract[0];
        	tanggalLahir = hari + " " + bulan + " " + tahun; 
        	penduduk.setTanggal_lahir(tanggalLahir);
    	}
    	
    	
    	return penduduk;
    	
    	
    }
    
    @Override
    public String masukanPenduduk(PendudukModel penduduk) {
    	KeluargaModel keluarga = keluargaMapper.selectKeluargaById(penduduk.getId_keluarga());
    	
    	if(keluarga == null) {
    		return "notfoundpenduduk";
    	} else {
    		String kodeKecamatan = pendudukMapper.lihatKodeKecamatan(penduduk.getId_keluarga());
        	
        	kodeKecamatan = kodeKecamatan.substring(0, kodeKecamatan.length()-1);
        	String[] tanggalLahir = penduduk.getTanggal_lahir().split("-");
        	tanggalLahir[0] = tanggalLahir[0].substring(2,4);

        	String digitAwal = "";
        	if(penduduk.getJenis_kelamin().equals("Wanita")) {
        		int tanggalLahir1 = Integer.parseInt(tanggalLahir[2]) + 40; 
        		digitAwal = kodeKecamatan + tanggalLahir1 + tanggalLahir[1] + tanggalLahir[0];	
        	} else {
        		digitAwal = kodeKecamatan + tanggalLahir[2] + tanggalLahir[1] + tanggalLahir[0];
        	}
        	
        	int count = 1;
        	String nik = digitAwal + "0001";
        	PendudukModel cekNikPenduduk = pendudukMapper.selectPenduduk(nik);
        	
        	while(cekNikPenduduk != null) {
            	String digitAkhirNik = cekNikPenduduk.getNik().substring(12,16);
            	int cekDigitAkhir = Integer.parseInt(digitAkhirNik) + count;
            	String digitAkhir = "";
            	if(cekDigitAkhir < 10) {
            		digitAkhir = "000" + cekDigitAkhir;
            	} else if(cekDigitAkhir >= 10 && cekDigitAkhir < 100) {
            		digitAkhir = "00" + cekDigitAkhir;	
            	} else if(cekDigitAkhir >= 100 && cekDigitAkhir < 1000) {
            		digitAkhir = "0" + cekDigitAkhir;
            	}
            	
            	nik = digitAwal + digitAkhir;
            	cekNikPenduduk = pendudukMapper.selectPenduduk(nik);
        	}
        	
        	penduduk.setNik(nik);
        	pendudukMapper.masukanPenduduk(penduduk);
        	return nik;
    	}
    }
    
    @Override
    public void updatePenduduk(PendudukModel penduduk, String nikLama) {
    	
    	PendudukModel dataLama = pendudukMapper.selectPenduduk(nikLama);
    	KeluargaModel keluargaLama = keluargaMapper.selectKeluargaById(dataLama.getId_keluarga());
    	KeluargaModel keluargaBaru = keluargaMapper.selectKeluargaById(penduduk.getId_keluarga());
    	
    	if(keluargaLama.getId_kelurahan().equals(keluargaBaru.getId_kelurahan()) && dataLama.getTanggal_lahir().equals(penduduk.getTanggal_lahir()) && dataLama.getJenis_kelamin().equals(penduduk.getJenis_kelamin())) {
    		penduduk.setNik(nikLama);
    	} else {
    		String kodeKecamatan = pendudukMapper.lihatKodeKecamatan(penduduk.getId_keluarga());
        	
        	kodeKecamatan = kodeKecamatan.substring(0, kodeKecamatan.length()-1);
        	String[] tanggalLahir = penduduk.getTanggal_lahir().split("-");
        	tanggalLahir[0] = tanggalLahir[0].substring(2,4);

        	String digitAwal = "";
        	if(penduduk.getJenis_kelamin().equals("Wanita")) {
        		int tanggalLahir1 = Integer.parseInt(tanggalLahir[2]) + 40; 
        		digitAwal = kodeKecamatan + tanggalLahir1 + tanggalLahir[1] + tanggalLahir[0];	
        	} else {
        		digitAwal = kodeKecamatan + tanggalLahir[2] + tanggalLahir[1] + tanggalLahir[0];
        	}
        	
        	int count = 1;
        	String nik = digitAwal + "0001";
        	PendudukModel cekNikPenduduk = pendudukMapper.selectPenduduk(nik);
        	
        	while(cekNikPenduduk != null) {
            	String digitAkhirNik = cekNikPenduduk.getNik().substring(12,16);
            	int cekDigitAkhir = Integer.parseInt(digitAkhirNik) + count;
            	String digitAkhir = "";
            	if(cekDigitAkhir < 10) {
            		digitAkhir = "000" + cekDigitAkhir;
            	} else if(cekDigitAkhir >= 10 && cekDigitAkhir < 100) {
            		digitAkhir = "00" + cekDigitAkhir;	
            	} else if(cekDigitAkhir >= 100 && cekDigitAkhir < 1000) {
            		digitAkhir = "0" + cekDigitAkhir;
            	}
            	
            	nik = digitAwal + digitAkhir;
            	cekNikPenduduk = pendudukMapper.selectPenduduk(nik);
        	}
        	
        	penduduk.setNik(nik);
    	}
    	
    	pendudukMapper.updatePenduduk(penduduk, nikLama);
    }
    
    @Override
    public void setWafat (String nik) {
    	pendudukMapper.setWafat(nik);
    	int id_keluarga = pendudukMapper.selectPenduduk(nik).getId_keluarga();
    	List<String> hidup = pendudukMapper.selectIdKeluarga(id_keluarga);
    	if(hidup.size() == 0) {
    		keluargaMapper.updateKK(id_keluarga);
    	}
    }
}
