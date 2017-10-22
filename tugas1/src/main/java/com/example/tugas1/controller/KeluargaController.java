package com.example.tugas1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tugas1.service.KeluargaService;
import com.example.tugas1.model.KeluargaModel;

@Controller
public class KeluargaController {

	@Autowired
    KeluargaService keluargaDAO;
	
	@RequestMapping("/keluarga")
    public String addSubmit (Model model,
            @RequestParam(value = "nkk") String nkk)
    {
	 	KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);
	 	if (keluarga != null) {
	 		model.addAttribute("title", "Data Keluarga");
            model.addAttribute ("keluarga", keluarga);
            return "lihat-keluarga";
        } else {
        	model.addAttribute("title", "Data Tidak Ditemukan");
            model.addAttribute ("nkk", nkk);
            return "not-found";
        }
    }
	
	@RequestMapping("/keluarga/tambah")
	public String tambahKeluarga (Model model) {
		model.addAttribute("title", "Tambah Keluarga");
		return "form-tambah-keluarga";
	}
	
	@RequestMapping(value="/keluarga/tambah", method = RequestMethod.POST)
	public String masukanKeluarga(Model model, KeluargaModel keluarga) 
	{
		String nkk = keluargaDAO.masukanKeluarga(keluarga);
		
		if(nkk.equals("notmatchkeluarga")) {
			model.addAttribute("title", "Gagal Menambahkan Data");
			model.addAttribute("flag", nkk);
			return "gagal-tambah";
		} else if (nkk.equals("notfoundkeluarga")) {
			model.addAttribute("title", "Gagal Menambahkan Data");
			model.addAttribute("flag", nkk);
			return "gagal-tambah";	
		} else {
			model.addAttribute("title", "Berhasil Menambahkan Data");
			model.addAttribute("berhasil", "tambah");
			model.addAttribute("nkk", nkk);
			return "berhasil";
		}
	}
	
	@RequestMapping("/keluarga/ubah")
	public String formUbah() {
		return "form-update";
	}
	
	@RequestMapping("/keluarga/ubah/{nkk}")
	public String ubahKeluarga (Model model, @PathVariable(value = "nkk") String nkk) 
	{
		KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);
		if(keluarga != null) {
			model.addAttribute("title","Ubah Data Keluarga");
			model.addAttribute("keluarga", keluarga);
			return "form-update-keluarga";
		} else {
			model.addAttribute("title", "Data Tidak Ditemukan");
			 model.addAttribute ("nkk", nkk);
			return "not-found";
		}
	}
	
	@RequestMapping(value="/keluarga/ubah/{nkk}", method = RequestMethod.POST)
	public String updateKeluarga (Model model, @PathVariable(value = "nkk") String nkk, KeluargaModel keluarga) {
		String hasil = keluargaDAO.updateKeluarga(keluarga, nkk);
		if(hasil.equals("notmatch")) {
			model.addAttribute("title", "Gagal Mengubah Data");
			model.addAttribute("flag", hasil);
			return "gagal-tambah";
		} else if (hasil.equals("notfound")) {
			model.addAttribute("title", "Gagal Mengubah Data");
			model.addAttribute("flag", hasil);
			return "gagal-tambah";	
		} else {
			model.addAttribute("title", "Berhasil Mengubah Data");
			model.addAttribute("berhasil", "ubah");
			model.addAttribute("nkk", nkk);
			return "berhasil";
		}
	}
}
