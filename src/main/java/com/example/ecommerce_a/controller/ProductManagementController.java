package com.example.ecommerce_a.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.form.AddNewPizzaForm;
import com.example.ecommerce_a.service.ItemService;
import com.example.ecommerce_a.service.ProductManagementService;

/**
 * 管理者関連のパスの受け渡しを行う.
 * 
 * @author yuki
 *
 */
@Controller
@RequestMapping("/admin")
public class ProductManagementController {

	@Autowired
	private ProductManagementService productManagementservice;

	@Autowired
	private ItemService itemService;

	@ModelAttribute
	public AddNewPizzaForm setUpAddNewPizzaForm() {
		return new AddNewPizzaForm();
	}

	/**
	 * 管理者の設定ページに遷移する.
	 * 
	 * @param model モデル
	 * @return 管理者設定ページ
	 */
	@RequestMapping("")
	public String admin(Model model) {
		return "admin_setting";
	}

	/**
	 * ピザを追加するページに遷移する.
	 * 
	 * @param model モデル
	 * @return ピザ追加ページ
	 */
	@RequestMapping("/toAddNewPizza")
	public String toAddNewPizza(Model model) {
		return "add_new_pizza";
	}

	/**
	 * ピザを追加する.
	 * 
	 * @param form   フォーム
	 * @param result リザルト
	 * @param model  モデル
	 * @return 管理者設定ページにリダイレクトする
	 * @throws IOException
	 */
	@RequestMapping("/addNewPizza")
	public String addNewPizza(@Validated AddNewPizzaForm form, BindingResult result, Model model, RedirectAttributes flash) throws IOException {
		// 画像が空ならエラー
		if (form.getImagePath().isEmpty()) {
			result.rejectValue("imagePath", null, "画像を選択してください ");
		}
		// 画像ファイル形式チェック
		MultipartFile imagePath = form.getImagePath();
		String fileExtension = null;
		System.out.println(imagePath.getOriginalFilename());
		try {
			fileExtension = getExtension(imagePath.getOriginalFilename());

			if (!"jpg".equals(fileExtension) && !"png".equals(fileExtension)) {
				result.rejectValue("imagePath", null, "拡張子は.jpgか.pngのみに対応しています");
			}
		} catch (Exception e) {
			result.rejectValue("imagePath", null, "拡張子は.jpgか.pngのみに対応しています");
		}

		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.out.println(error.getDefaultMessage());
			}
			return toAddNewPizza(model);
		}

		Item item = new Item();
		BeanUtils.copyProperties(form, item);
		item.setPriceM(Integer.parseInt(form.getPriceM()));
		item.setPriceL(Integer.parseInt(form.getPriceL()));
		item.setImagePath(form.getImagePath().getOriginalFilename());

		// 画像ファイルをIMGフォルダに保存する
		String filename = item.getImagePath();
		Path uploadfile = Paths.get("src/main/resources/static/img/" + filename);
		try (OutputStream os = Files.newOutputStream(uploadfile, StandardOpenOption.CREATE)) {
			byte[] bytes = form.getImagePath().getBytes();
			os.write(bytes);
		} catch (IOException ex) {
			System.err.println(ex);
		}
		
		productManagementservice.insertPizza(item);
		flash.addFlashAttribute("message", "新商品を追加しました");

		return "redirect:/admin";
	}

	/**
	 * 商品管理ページに遷移する.
	 * 
	 * @param model モデル
	 * @return 商品管理ページ
	 */
	@RequestMapping("/toItemConfig")
	public String toItemConfig(Model model) {
		List<Item> itemList = itemService.findAll();
		model.addAttribute("itemList", itemList);
		return "item-config";
	}

	/**
	 * 商品詳細ページに遷移する.
	 * 
	 * @param id ピザのID
	 * @param model モデル
	 * @return 商品詳細ページ
	 */
	@RequestMapping("/showDetail")
	public String detailItem(Integer id, Model model) {
		Item item = itemService.load(id);
		model.addAttribute("item", item);
		return "item_detail";
	}

	/**
	 * 商品を削除する.
	 * 
	 * @param id　ピザのID
	 * @return 管理者設定ページ
	 */
	@RequestMapping("/deleteItem")
	public String deleteItem(Integer id, RedirectAttributes flash) {
		productManagementservice.delete(id);
		flash.addFlashAttribute("message", "商品を削除しました");
		return "redirect:/admin";
	}

	/*
	 * ファイル名から拡張子を返します.
	 * 
	 * @param originalFileName ファイル名
	 * 
	 * @return .を除いたファイルの拡張子
	 */
	private String getExtension(String originalFileName) throws Exception {
		if (originalFileName == null) {
			throw new FileNotFoundException();
		}
		int point = originalFileName.lastIndexOf(".");
		if (point == -1) {
			throw new FileNotFoundException();
		}
		return originalFileName.substring(point + 1);
	}

}
