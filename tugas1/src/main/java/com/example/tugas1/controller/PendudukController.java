package com.example.tugas1.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tugas1.service.PendudukService;
import com.example.tugas1.model.PendudukModel;
import com.example.tugas1.service.DomisiliService;
import com.example.tugas1.model.KotaModel;
import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KelurahanModel;

@Controller
public class PendudukController 
{
	@Autowired
    PendudukService pendudukDAO;
	@Autowired
    DomisiliService domisiliDAO;
	boolean flag = false;
	
	@RequestMapping("/")
    public String index (Model model)
    {
		model.addAttribute("title", "Home");
        return "home";
    }
	
	@RequestMapping("/penduduk")
    public String lihatPenduduk (Model model,
            @RequestParam(value = "nik") String nik)
    {
		if (flag == true) {
			flag = false;
			model.addAttribute("berhasil", "setwafat");
			model.addAttribute("flag", "nik");
			model.addAttribute("nik", nik);
			return "berhasil";
		} else {
			PendudukModel penduduk = pendudukDAO.selectPenduduk(nik, null);
		 	if (penduduk != null) {
		 		model.addAttribute("title", "Data Penduduk");
	            model.addAttribute ("penduduk", penduduk);
	            return "lihat-penduduk";
	        } else {
	        	model.addAttribute("title", "Data Tidak Ditemukan");
	        	model.addAttribute("flag", "nik");
	            model.addAttribute ("nik", nik);
	            return "not-found";
	        }
		}
    }
	
	@RequestMapping("/penduduk/tambah")
	public String tambahPenduduk (Model model) {
		model.addAttribute("title", "Tambah Penduduk");
		return "form-tambah-penduduk";
	}
	
	@RequestMapping(value="/penduduk/tambah", method = RequestMethod.POST)
	public String masukanPenduduk(Model model, PendudukModel penduduk) 
	{
		String nik = pendudukDAO.masukanPenduduk(penduduk);
		if(nik.equals("notfoundpenduduk")) {
			model.addAttribute("title", "Gagal Menambahkan Data");
			model.addAttribute("flag", nik);
			return "gagal-tambah";
		} else {
			model.addAttribute("title", "Berhasil Menambahkan Data");
			model.addAttribute("flag", "nik");
			model.addAttribute("berhasil", "tambah");
			model.addAttribute("nik", nik);
			return "berhasil";
		}
	}
	
	@RequestMapping("/penduduk/ubah")
	public String formUbah() {
		return "form-update";
	}
	
	@RequestMapping("/penduduk/ubah/{nik}")
	public String ubahPenduduk (Model model, @PathVariable(value = "nik") String nik) 
	{
		PendudukModel penduduk = pendudukDAO.selectPenduduk(nik, "ubah");
		if(penduduk != null) {
			model.addAttribute("title","Ubah Data Penduduk");
			model.addAttribute("penduduk", penduduk);
			return "form-update-penduduk";
		} else {
			model.addAttribute("title", "Data Tidak Ditemukan");
        	model.addAttribute("flag", "nik");
            model.addAttribute ("nik", nik);
			return "not-found";
		}
	}
	
	@RequestMapping(value="/penduduk/ubah/{nik}", method = RequestMethod.POST)
	public String updatePenduduk (Model model, @PathVariable(value = "nik") String nik, PendudukModel penduduk) {
		pendudukDAO.updatePenduduk(penduduk, nik);
		model.addAttribute("title", "Berhasil Mengubah Data");
		model.addAttribute("flag", "nik");
		model.addAttribute("berhasil", "ubah");
		model.addAttribute("nik", nik);
		return "berhasil";
	}
	
	@RequestMapping(value="/penduduk/mati", method = RequestMethod.POST)
	public String updatePenduduk (Model model, String nik) {
		pendudukDAO.setWafat(nik);
		model.addAttribute("nik", nik);
		model.addAttribute("flag", "nik");
		flag = true;
		return "redirect:/penduduk?nik=" + nik;
	}
	
	@RequestMapping("penduduk/cari")
	public String cariKota(Model model,
			@RequestParam(value = "kt", required=false) String kt,
			@RequestParam(value = "kc", required=false) String kc,
			@RequestParam(value = "kl", required=false) String kl) {
		
		String hasil = "";
		if(kt == null && kc == null && kl == null) {
			List<KotaModel> allKota = domisiliDAO.selectAllKota();
			model.addAttribute("title", "Cari Penduduk");
			model.addAttribute("allKota", allKota);
			hasil = "form-cari-kota";
		} else if (kt != null && kc == null && kl == null) {
			String nama_kota = domisiliDAO.selectNamaKota(kt);
			List<KecamatanModel> allKecamatan = domisiliDAO.selectAllKecamatan(kt);
			model.addAttribute("title", "Cari Penduduk");
			model.addAttribute("id_kota", kt);
			model.addAttribute("nama_kota", nama_kota);
			model.addAttribute("allKecamatan", allKecamatan);
			hasil = "form-cari-kecamatan";
		} else if (kt != null && kc != null && kl == null) {
			List<KelurahanModel> allKelurahan = domisiliDAO.selectAllKelurahan(kc);
			String nama_kota = domisiliDAO.selectNamaKota(kt);
			String nama_kecamatan = domisiliDAO.selectNamaKecamatan(kc);
			model.addAttribute("title", "Cari Penduduk");
			model.addAttribute("id_kota", kt);
			model.addAttribute("nama_kota", nama_kota);
			model.addAttribute("id_kecamatan", kc);
			model.addAttribute("nama_kecamatan", nama_kecamatan);
			model.addAttribute("allKelurahan", allKelurahan);
			hasil = "form-cari-kelurahan";
		} else if (kt != null && kc != null && kl != null) {
			List<PendudukModel> dataPenduduk = domisiliDAO.selectAllPenduduk(kl);
			model.addAttribute("dataPenduduk", dataPenduduk);
			hasil = "data-penduduk";
		}
		
		return hasil;
	}
}