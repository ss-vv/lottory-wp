package com.xhcms.lottery.admin.web.action.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.ImageFilter;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.FileDictionary;
import com.octo.captcha.component.word.wordgenerator.ComposeDictionaryWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

/**
 * 
 * @desc 管理生成验证图片信息的引擎类
 * @author lilei
 * @date 2012-7-17
 * @version 1.0
 */
public class ImageEngine extends ListImageCaptchaEngine {
	@Override
	protected void buildInitialFactories() {
		int minWordLength = 4;	//生成验证码的最小长度
		int maxWordLength = 4;	//生成验证码的最大长度
		
		int fontSize = 18;		//图片字体大小
		int imageWidth = 80;	//图片宽度
		int imageHeight = 30;	//图片高度

		// word generator
		WordGenerator dictionnaryWords = new ComposeDictionaryWordGenerator(
				new FileDictionary("toddlist"));

		// word2image components
		TextPaster randomPaster = new DecoratedRandomTextPaster(minWordLength,
				maxWordLength, new RandomListColorGenerator(new Color[] {
						new Color(23, 170, 27), new Color(220, 34, 11),
						new Color(23, 67, 172) }), new TextDecorator[] {});
		BackgroundGenerator background = new UniColorBackgroundGenerator(
				imageWidth, imageHeight, new Color(209,225,244));
		FontGenerator font = new RandomFontGenerator(fontSize, fontSize,
				new Font[] {
						new Font("nyala", Font.BOLD, fontSize),
						new Font("Bell MT", Font.PLAIN, fontSize),
						new Font("Credit valley", Font.BOLD, fontSize)
					});

		ImageDeformation postDef = new ImageDeformationByFilters(
				new ImageFilter[] {});
		ImageDeformation backDef = new ImageDeformationByFilters(
				new ImageFilter[] {});
		ImageDeformation textDef = new ImageDeformationByFilters(
				new ImageFilter[] {});

		WordToImage word2image = new DeformedComposedWordToImage(font,
				background, randomPaster, backDef, textDef, postDef);
		addFactory(new GimpyFactory(dictionnaryWords, word2image));
	}

}