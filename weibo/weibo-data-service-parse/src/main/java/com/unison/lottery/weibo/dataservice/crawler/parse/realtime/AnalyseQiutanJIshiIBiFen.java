package com.unison.lottery.weibo.dataservice.crawler.parse.realtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.lang3.StringUtils;

import com.unison.lottery.weibo.dataservice.crawler.parse.fenxi.as;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallMatchModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.util.DateFormateUtil;
import com.unison.lottery.weibo.dataservice.crawler.util.ValueConvertUtil;

public class AnalyseQiutanJIshiIBiFen {

	public static void main(String[] args) {
		String footballData = "20141023175033$$欧洲杯^67^1!巴西甲^4^1!欧青U21外^114^1!韩K联^15^1!日联杯^72^1!阿根廷杯^1183^1!欧青U19^215^0!欧女杯^534^0!瑞典乙南^367^0!女欧U17^526^0!挪威U19^628^0!捷克杯^116^0!中女超^645^0!球会友谊^41^0!智利杯^714^0!阿联杯^733^0!女亚冠U16^435^0!瑞U19^846^0!立陶乙^897^0!尼日超^949^0!西丁^951^0!比U21^969^0!马尔乙^1000^0!丹麦U17^1041^0!伊拉联^1054^0!约旦甲^1078^0!亚青U19^399^0!乌兹甲^1160^0!巴圣杯^1358^0!国际友谊^1366^0!比荷女联^1270^0$$1057413^15^-1^20141009130000^20141009140407^蔚山现代^FC首尔^0^3^0^1^1^0^1^2^0.25^2.30^3.00^3.40^7^6^!1067093^645^-1^20141009130000^^河南十三香女足^解放军女足^2^2^0^0^0^0^0^0^^^^^^^!1067094^645^-1^20141009140000^^广西壮族女足^大连实德女足^0^5^0^0^0^0^0^0^^^^^^^!1067095^645^-1^20141009140000^^杭州西子女足^上海SVA女足^0^4^0^0^0^0^0^0^^^^^^^!1067096^645^-1^20141009140000^^陕西欧亚女足^天津汇森女足^0^4^0^0^0^0^0^0^^^^^^^!1067097^645^-1^20141009150000^^长春华信女足^河北远东女足^1^1^0^0^0^0^0^0^^^^^^^!1013533^949^-1^20141009150000^^内贝城^冈贝联^2^2^1^1^0^0^0^0^^^^^20^16^!1067098^645^-1^20141009160000^^山东女足^北京兆泰女足^2^2^0^0^0^0^0^0^^^^^^^!1067099^645^-1^20141009160000^^武汉江汉大学女足^江苏华泰女足^2^2^0^0^0^0^0^0^^^^^^^!1067100^645^-1^20141009160000^^四川剑南春女足^广东海印女足^1^0^0^0^0^0^0^0^^^^^^^!1063054^435^-1^20141009163000^20141009173123^中国台北女足U16(中)^缅甸女足U16^2^0^1^0^0^0^0^0^-0.25^^^^^^!1063055^435^-1^20141009163000^20141009173324^菲律賓女足U16(中)^柬埔寨女足U16^4^2^3^1^0^0^0^0^1.25^^^^^^!1060272^526^-1^20141009170000^20141009175949^克罗地亞女足U17^瑞典女足U17^1^5^1^1^0^0^0^0^-3.75^^^^^^!1064568^399^-1^20141009170000^20141009180716^伊朗U19(中)^泰国U19^1^2^1^0^0^0^0^3^1.25^1.44^4.20^6.00^^^!1064580^399^-1^20141009170000^20141009180449^韩国U19(中)^越南U19^6^0^1^0^0^0^0^1^1^1.40^4.20^7.00^^^!1065054^1366^-1^20141009170000^20141009175749^斯洛文尼亚U16^塞尔维亚U16^5^2^2^1^0^0^0^0^-0.25^^^^^^!1063785^215^-1^20141009180000^20141009190226^德国U19(中)^哈萨克斯坦U19^6^0^1^0^0^0^1^2^4.5^1.01^21.00^51.00^^^!1061848^72^-1^20141009180000^20141009190526^广岛三箭^柏太阳神^2^0^1^0^0^1^2^4^0.5^2.30^3.30^3.10^8^9^!1061849^72^-1^20141009180000^20141009190119^大阪钢巴^川崎前锋^3^1^2^0^0^0^0^0^0.5^1.83^3.60^4.20^2^4^!1065056^951^-1^20141009180000^20141009190553^阿尔兹拉^木罗^1^1^1^1^0^0^4^4^0.25^^^^^^!1064654^897^-1^20141009190000^20141009195722^乌田纳^卡斯路鲁达^4^1^3^0^0^0^0^0^^^^^5^2^!1039875^1160^-1^20141009190000^^阿兰加^库卢维赤克肯德^1^3^0^0^0^0^0^0^^^^^10^1^!1063793^215^-1^20141009200000^20141009210044^爱沙尼亚U19(中)^土耳其U19^0^0^0^0^0^0^0^3^-1.5^11.00^7.00^1.20^^^!1063853^215^-1^20141009200000^20141009210132^芬兰U19(中)^挪威U19^0^0^0^0^0^0^2^1^-0.5^5.00^3.50^1.72^^^!1064569^399^-1^20141009200000^20141009210506^缅甸U19^也门U19^0^0^0^0^0^0^2^4^1.25^1.28^5.25^8.00^^^!1064581^399^-1^20141009200000^20141009210353^日本U19(中)^中国U19^1^2^1^1^0^0^0^3^1.25^1.40^4.33^6.50^^^!1039874^1160^-1^20141009200000^^萨马尔罕沙洲^奥波德^1^2^0^0^0^0^0^0^^^^^12^4^!1063435^1054^-1^20141009203000^^纳弗特^纳夫特米桑^2^2^1^2^0^0^0^0^^^^^10^5^!1063786^215^-1^20141009210000^20141009220121^拉脫维亚U19^奧地利U19^0^1^0^0^0^0^4^3^-0.75^6.50^4.50^1.44^^^!1063803^215^-1^20141009210000^20141009220306^北爱尔兰U19^捷克U19^0^2^0^2^0^0^2^2^-0.5^3.00^3.50^2.25^^^!1060273^526^-1^20141009210000^20141009215933^苏格兰女足U17(中)^黑山女足U17^9^0^4^0^0^0^0^0^3.75^^^^^^!1066395^1078^-1^20141009210000^^沙克胡森^艾沙尔特^1^0^0^0^0^0^0^0^^^^^^^!1064594^733^-1^20141009211000^20141009221831^阿尔艾茵^伊斯兰卡尔巴^0^1^0^0^0^0^3^3^2^1.61^4.20^4.00^7^14^!1064595^733^-1^20141009211000^20141009222135^纳萨^亚吉拉^1^0^0^0^0^0^4^1^-0.25^1.80^3.75^3.70^4^2^!1066396^1078^-1^20141009211500^^萨哈布^雅莫科^1^0^0^0^1^0^0^0^^^^^^^!1063833^215^-1^20141009213000^20141009223305^波兰U19^摩尔多瓦U19^4^1^2^0^0^0^1^2^1.25^1.30^5.25^9.50^^^!1063834^215^-1^20141009213000^20141009223152^荷兰U19(中)^安道尔U19^7^0^1^0^0^0^0^0^6.25^^^^^^!1063794^215^-1^20141009220000^20141009230131^克罗地亚U19^冰岛U19^4^1^0^0^0^0^2^1^2^1.16^7.00^15.00^^^!1064761^1366^-1^20141009223000^20141009233452^德国U20(中)^英格兰U20^0^1^0^1^0^0^2^3^0.25^2.00^3.60^3.20^^^!1063854^215^-1^20141009230000^20141010000013^立陶宛U19^苏格兰U19^0^0^0^0^0^0^1^2^-0.75^7.00^4.33^1.45^^^!1065057^1366^-1^20141009230000^20141010000717^乌克兰U23^捷克U23^0^1^0^0^0^0^0^1^-0.25^^^^^^!1065055^846^-1^20141009233000^20141010002631^奥斯达U19^卡马亚U19^4^2^1^1^0^0^0^0^-0.75^^^^^^!1064759^1366^-1^20141009233000^20141010003845^卡塔尔^黎巴嫩^5^0^1^0^0^0^1^1^0.75^1.61^3.50^5.25^96^121^!1059846^116^-1^20141009235900^20141010010345^波希米亚1905^亚布洛内茨^0^2^0^1^0^0^1^2^-0.25^3.25^3.40^2.15^6^3^!1064615^733^-1^20141009235900^20141010010810^艾维赫达^阿尔阿赫利^1^3^0^1^0^0^2^2^-0.25^3.40^3.60^1.85^1^5^!1065020^1366^-1^20141009235900^20141010010545^捷克U20^法国U20^1^2^1^1^0^0^2^1^-0.25^3.00^3.50^2.10^^^!1062767^114^-1^20141010003000^20141010013309^荷兰U21(中)^葡萄牙U21^0^2^0^1^0^0^3^3^0^2.45^3.40^2.75^^^!1065050^41^-1^20141010003000^20141010013108^斯特劳斯堡^柏林联合^0^3^0^1^0^0^0^0^-1.5^9.00^6.00^1.20^^17^!1065058^41^-1^20141010003000^20141010013346^亚伦^奥格斯堡^7^1^3^1^0^0^0^0^^3.00^3.60^2.00^14^10^!1064762^1366^-1^20141010003000^20141010013209^波兰U20^意大利U20^2^1^1^0^0^0^0^1^-0.25^2.75^3.40^2.30^^^!1065046^1366^-1^20141010003000^20141010013409^丹麦U19^乌克兰U19^1^0^1^0^0^0^0^0^0^2.37^3.40^2.62^^^!982759^628^-1^20141010004500^^霍尔莫利亚U19^罗伦斯固克U19^1^2^0^0^0^0^0^0^^^^^11^9^!982760^628^-1^20141010004500^^巴洛姆U19^斯吉德U19^7^0^4^0^0^0^0^0^^^^^4^7^!982761^628^-1^20141010004500^^艾斯卡U19^斯塔贝克U19^1^1^0^0^0^0^0^0^^^^^6^1^!982762^628^-1^20141010004500^^弗洛U19^奥普沙尔U19^1^0^0^0^0^0^0^0^^^^^8^10^!982757^628^-1^20141010010000^20141010015733^利勒斯特罗姆U19^瓦勒伦加U19^0^2^0^0^0^0^0^2^^3.60^5.50^1.53^3^2^!1065059^41^-1^20141010010000^20141010020209^达姆斯达特^美因茨^0^2^0^2^0^0^0^0^-0.25^2.70^3.60^2.15^6^6^!1065060^41^-1^20141010010000^20141010020109^帕尔马^顿涅茨克矿工^1^0^0^0^0^0^2^1^0^2.30^3.40^2.75^18^3^!1064656^1041^-1^20141010010000^^瓦埃勒U17^哥本哈根U17^1^1^0^0^0^0^0^0^^^^^^^!1065047^1366^-1^20141010010000^20141010020309^挪威U21^爱尔兰U21^4^1^2^0^0^0^2^1^0.25^^^^^^!963324^367^-1^20141010013000^20141010023510^哈米亚^奥迪沃特^2^1^1^1^0^1^2^2^0.25^1.80^3.75^4.10^11^5^!1064658^41^-1^20141010013000^20141010022834^波斯坦纳斯女足^埃斯基尔图纳女足^1^1^1^1^0^0^4^3^^7.00^6.00^1.25^10^6^90,1-1;;1,1-4;;2!1065061^969^-1^20141010013000^20141010023310^安德莱赫特U21^苗斯干U21^3^0^2^0^0^0^2^3^2.25^1.12^7.50^11.00^^^!1058249^1000^-1^20141010013000^^费古拉^比尔泽布瓜^1^1^0^0^0^0^0^0^^^^^10^14^!1064760^1366^-1^20141010013000^20141010023615^荷兰U20^土耳其U20^2^2^1^1^1^0^3^2^0.5^1.66^3.80^4.33^^^!1048286^1270^-1^20141010013000^20141010023310^标准列治女足^阿贾克斯女足^1^0^0^0^0^0^0^0^0.25^1.80^4.00^3.40^2^3^!1063804^215^-1^20141010020000^20141010030105^俄罗斯U19(中)^法罗群岛U19^7^0^5^0^0^0^1^2^4.5^1.02^17.00^34.00^^^!1065062^969^-1^20141010020000^20141010030717^布鲁日U21^亨克U21^0^0^0^0^0^0^3^4^-1^4.00^3.80^1.66^^^!1065019^1366^-1^20141010020000^20141010030416^西班牙U17^德国U17^2^0^0^0^0^0^0^0^0.25^^^^^^!1063901^534^-1^20141010023000^20141010033420^布雷西亚女足^里昂女足^0^5^0^3^0^0^1^0^-3.5^19.00^13.00^1.04^3^1^!1063902^534^-1^20141010023000^20141010033245^拉禾尼女足^布里斯托学院女足^0^4^0^1^0^0^2^1^-2.5^17.00^9.00^1.08^^7^!974988^67^-1^20141010024500^20141010034718^斯洛伐克^西班牙^2^1^1^0^0^0^4^3^-1^9.00^4.50^1.44^40^8^!974989^67^-1^20141010024500^20141010035018^马其顿^卢森堡^3^2^1^2^0^0^3^5^1.25^1.57^4.00^7.00^112^127^!974990^67^-1^20141010024500^20141010034718^白俄罗斯^乌克兰^0^2^0^0^0^0^1^2^-0.25^3.80^3.25^2.20^89^24^!975056^67^-1^20141010024500^20141010034552^立陶宛^爱沙尼亚^1^0^0^0^0^1^2^3^0.25^2.30^3.10^3.70^103^81^!975057^67^-1^20141010024500^20141010034718^英格兰^圣马力诺^5^0^2^0^0^0^1^2^5.75^1.01^41.00^101.00^18^208^!975058^67^-1^20141010024500^20141010034718^斯洛文尼亚^瑞士^1^0^0^0^0^0^2^1^-0.25^3.75^3.30^2.20^53^10^!975147^67^-1^20141010024500^20141010034818^列支敦士登^黑山^0^0^0^0^0^0^3^0^-1.75^15.00^6.25^1.25^172^43^!975148^67^-1^20141010024500^20141010034818^瑞典^俄罗斯^1^1^0^1^0^0^3^2^0.25^2.87^3.20^2.75^32^23^!975149^67^-1^20141010024500^20141010034611^摩尔多瓦^奧地利^1^2^1^1^0^1^1^3^-0.75^6.50^3.90^1.61^105^39^!1064758^1366^-1^20141010030000^20141010040549^摩洛哥^中非共和国^4^0^2^0^0^0^2^3^1.25^1.30^4.75^8.50^87^137^!1063875^714^-1^20141010040000^20141010050337^科金博(中)^安托法加斯塔^0^3^0^0^0^1^3^2^-0.5^5.00^3.50^1.65^10^15^!974625^4^-1^20141010063000^20141010073210^弗鲁米嫩塞^米内罗竞技^0^0^0^0^0^0^1^2^0.25^2.05^3.40^3.50^7^5^!974627^4^-1^20141010063000^20141010073730^桑托斯^巴伊亚^1^0^1^0^0^0^1^1^0.75^1.61^3.80^5.50^8^15^!1063876^714^-1^20141010070000^20141010080344^库里科^奥达斯^0^0^0^0^0^2^4^2^-0.25^2.60^3.20^2.50^9^10^!1064657^1358^-1^20141010070000^20141010080057^保地花高SP^皮拉西卡巴^1^1^1^0^0^0^0^0^0.5^1.72^3.75^4.00^^^!1063008^1183^-1^20141010071000^20141010081439^河床(中)^罗萨里奥中央^0^0^0^0^0^0^1^2^0.75^2.30^3.10^3.25^1^12^90,0-0;;;4-5;2!974633^4^-1^20141010073000^20141010083433^沙佩科恩斯^巴西国际^5^0^2^0^0^1^0^1^-0.25^3.30^3.10^2.30^17^3^!1062781^1366^-1^20141010090000^20141010100257^墨西哥^洪都拉斯^2^0^2^0^0^0^1^1^1.25^1.30^5.25^9.50^16^56^";
		String basketData = "Euro^7^1!南美俱杯^233^1!NBA^1^1!阿甲^302^1$$197824^7^#E87100^20141017010000^0^^塞德华塔^乌尼卡哈^^^-7.5^^^^^^^^^^^^^^!197879^7^#E87100^20141017010000^0^^艾菲斯比逊^犹里卡斯^^^2.5^^^^^^^^^^^^^^!197825^7^#E87100^20141017020500^0^^TA马卡比^利摩日^^^11^^^^^^^^^^^^^^!197880^7^#E87100^20141017024500^0^^贝尔格莱德红星^加拉塔沙雷^^^2^^^^^^^^^^^^^^!197862^7^#E87100^20141017030000^0^^华伦西亚^奥林匹亚科斯^^^2.5^^^^^^^^^^^^^^!195409^233^#FFA13D^20141017044500^0^^马竞阿根廷胡宁^伊波塔多拉^^^^^^^^^^^^^^^^^!197085^1^#FF0000^20141017070000^0^^费城76人^波士顿凯尔特人^^^^^^^^^^^^^^^^^!195410^233^#FFA13D^20141017070000^0^^阿瓜达竞技^利美拉胜利者^^^^^^^^^^^^^^^^^!197086^1^#FF0000^20141017080000^0^^金州勇士^丹佛掘金^^^^^^^^^^^^^^^^^!197087^1^#FF0000^20141017080000^0^^新奥尔良鹈鹕^俄克拉荷马城雷霆^^^^^^^^^^^^^^^^^!197088^1^#FF0000^20141017080000^0^^芝加哥公牛^亚特兰大老鹰^^^^^^^^^^^^^^^^^!195256^302^#00A0F3^20141017090000^0^^BB学生队^博卡青年^^^^^^^^^^^^^^^^^!197089^1^#FF0000^20141017100000^0^^洛杉矶湖人^犹他爵士^^^^^^^^^^^^^^^^^!197090^1^#FF0000^20141017100000^0^^菲尼克斯太阳^圣安东尼奥马刺^^^^^^^^^^^^^^^^^";
		// String tennisData =
		// "1417^ATP斯德哥尔摩公开赛^ATP斯德哥爾摩公開賽^室内硬地!1418^ATP克里姆林杯^ATP克里姆林盃^室内硬地!1479^WTA克里姆林杯^WTA克里姆林盃^室内硬地!1480^卢森堡公开赛 ^盧森堡公開賽^室内硬地!1508^奥地利银行公开赛^奧地利銀行公開賽^室内硬地$$89882^1417^1^第二轮^20141016180000^0^赫比特 ^赫比特 ^^^卡皮尔^卡皮爾^^^^^^^^^^^^^^^^^^^^^^^^^^^!89883^1417^1^第二轮^20141016200000^0^查迪^J.查迪^^^J.索克^傑克-索克^^^^^^^^^^^^^^^^^^^^^^^^^^^!89850^1417^1^第二轮^20141016200000^0^M．巴青格尔^M．巴青格爾^^^L-梅耶尔^L·梅耶爾^^^^^^^^^^^^^^^^^^^^^^^^^^^!89881^1417^1^第二轮^20141017003000^0^伯蒂奇^貝迪治^^^布朗^布朗恩^^^^^^^^^^^^^^^^^^^^^^^^^^^!89849^1417^1^第二轮^20141017023000^0^多尔戈波洛夫^多爾戈波洛夫 ^^^马纳里诺^馬納裏諾^^^^^^^^^^^^^^^^^^^^^^^^^^^!89853^1417^3^八强^20141016180000^0^H.孔蒂宁^亨利-孔蒂寧^涅米宁^尼米倫^布托拉克^B·布托拉克^R.克拉森^R.克拉森^^^^^^^^^^^^^^^^^^^^^^^^^!89884^1417^3^八强^20141016220000^0^肯亚基塞^肯亞基塞^扬格^D·揚格^布朗^布朗恩^斯捷史托姆^安德烈-斯捷史托姆^^^^^^^^^^^^^^^^^^^^^^^^^!89885^1417^3^八强^20141016220000^0^J·穆雷^J·穆雷^J·皮亚斯^皮亞斯^库伯特^L·庫伯特 ^里斯托^R·里斯托^^^^^^^^^^^^^^^^^^^^^^^^^!89852^1417^3^八强^20141017000000^0^迪米托夫^迪米度夫^赫比特 ^赫比特 ^康拉德^康拉德^J.索克^傑克-索克^^^^^^^^^^^^^^^^^^^^^^^^^!89858^1418^1^第二轮^20141016171500^2^古库舒金^古庫舒金 ^^^弗格尼尼^F·弗吉尼里^^^6^0^^^^^^^^^15^1^4^0^^^^^^^^^0^0^1!89854^1418^1^第二轮^20141016190000^0^拉奥尼奇^米洛斯·拉奧歷^^^比兰基斯^比蘭基斯^^^^^^^^^^^^^^^^^^^^^^^^^^^!89887^1418^1^第二轮^20141016190000^0^罗布雷多^羅比度^^^卡拉吉诺维奇^卡拉吉諾維奇^^^^^^^^^^^^^^^^^^^^^^^^^^^!89843^1418^1^第二轮^20141016210000^0^古尔比斯^E·古比斯^^^特拉沃^D·特拉弗爾^^^^^^^^^^^^^^^^^^^^^^^^^^^!89886^1418^1^第二轮^20141016230000^0^尤兹尼^尤斯尼^^^摩纳哥^祖安莫納科 ^^^^^^^^^^^^^^^^^^^^^^^^^^^!89888^1418^3^八强^20141016190000^0^罗伦兹^羅倫茲^塞皮^A·塞皮^塞马克^F·塞马克^维斯利^維斯利^^^^^^^^^^^^^^^^^^^^^^^^^!89889^1418^3^八强^20141016210000^0^布拉希亚利^D·布拉希亞利^斯塔雷斯^P·斯泰韋塞^卡拉夫楚克^康斯坦丁-卡拉夫楚克 ^库内特索夫^庫內特索夫^^^^^^^^^^^^^^^^^^^^^^^^^!89891^1418^3^八强^20141016210000^0^拉约维卡^拉約維卡^斯库高^斯庫高 ^S·格罗斯^S.戈羅斯^古希奥尼^C·古希奧尼^^^^^^^^^^^^^^^^^^^^^^^^^!89890^1418^3^八强^20141016230000^0^塞列坦尼^塞列坦尼^弗格尼尼^F·弗吉尼里^多斯科伊^多斯科伊^鲁布廖夫.安德烈^鲁布廖夫.安德烈^^^^^^^^^^^^^^^^^^^^^^^^^!89879^1479^2^第二轮^20141016171000^2^古历尼克^古歷尼克^^^皮隆科娃^T·彼諾科娃^^^2^2^^^^^^^^^30^0^6^0^^^^^^^^^40^1^2!89904^1479^2^第二轮^20141016190000^0^斯尼科娃^斯尼科娃^^^马拉德诺维奇^美拉德諾維奇^^^^^^^^^^^^^^^^^^^^^^^^^^^!89903^1479^2^第二轮^20141016210000^0^里斯克^裏斯克^^^帕夫尤申科娃^A·帕夫尤申科娃^^^^^^^^^^^^^^^^^^^^^^^^^^^!89875^1479^2^第二轮^20141017010000^0^奇布尔科娃^齊布科娃^^^戴蒂查科^戴蒂查科^^^^^^^^^^^^^^^^^^^^^^^^^^^!89907^1479^4^八强^20141016171000^2^卡罗琳.加西亚^卡羅琳.加西亞^桑通加^派拿^R·奥拉鲁^R·奧拉魯^皮尔^S·皮亞^6^3^^^^^^^^^30^1^1^2^^^^^^^^^0^0^1!89908^1479^4^八强^20141016171000^2^V.库德维雅^V.库德维雅^罗迪纳^E·罗迪纳^加斯帕亚^加斯帕亞^帕诺娃^帕諾娃^3^1^^^^^^^^^0^0^6^2^^^^^^^^^0^1^1!89906^1479^4^八强^20141016230000^0^古历尼克^古歷尼克^斯尼科娃^斯尼科娃^L.基切洛克^L.基切洛克^莎维楚克^O·莎維楚克^^^^^^^^^^^^^^^^^^^^^^^^^!89880^1479^4^八强^20141016230000^0^柏古^柏古^罗迪欧诺娃^A·羅迪歐諾娃^辛吉斯^軒芝絲^佩内塔^F·潘纳塔^^^^^^^^^^^^^^^^^^^^^^^^^!89898^1480^2^八强^20141016180000^0^迈耶^迈耶^^^A·贝克^A·貝克^^^^^^^^^^^^^^^^^^^^^^^^^^^!89901^1480^2^八强^20141016200000^0^布汀娜^布汀娜^^^贝尔滕斯^基基-貝爾滕斯^^^^^^^^^^^^^^^^^^^^^^^^^^^!89900^1480^2^八强^20141016230000^0^拉尔森^拉爾森^^^斯特里科娃^B·史倫高娃^^^^^^^^^^^^^^^^^^^^^^^^^^^!89899^1480^2^八强^20141017010000^0^德尼莎^德尼莎^^^勒普琴科^V·勒普琴科^^^^^^^^^^^^^^^^^^^^^^^^^^^!89872^1480^4^八强^20141016193000^0^加里奎斯^梅蒂娜^西尔维亚^西爾維亞^埃莱娜-波格丹^埃萊娜-波格丹^梅里查尔^梅里查尔^^^^^^^^^^^^^^^^^^^^^^^^^!89902^1480^4^八强^20141016213000^0^哈尔德卡^L·哈迪卡^卡雷茨科娃^卡雷茨科娃^瑞伊^瑞伊^史密丝^史密丝^^^^^^^^^^^^^^^^^^^^^^^^^!89893^1508^1^第二轮^20141016183000^0^罗索尔^L.羅素^^^特洛伊基^特洛伊基^^^^^^^^^^^^^^^^^^^^^^^^^^^!89861^1508^1^第二轮^20141016190000^0^B·贝克尔 ^B·贝克尔^^^哈塞^R·克亚斯^^^^^^^^^^^^^^^^^^^^^^^^^^^!89862^1508^1^第二轮^20141016210000^0^贝卢西^比鲁斯^^^菲·洛佩兹^盧比斯^^^^^^^^^^^^^^^^^^^^^^^^^^^!89894^1508^1^第二轮^20141016223000^0^勒纳德^勒納德 ^^^斯塔霍夫斯基^S·斯塔克何斯基^^^^^^^^^^^^^^^^^^^^^^^^^^^!89892^1508^1^第二轮^20141016230000^0^费雷尔^費拿^^^卡姆克^卡姆克^^^^^^^^^^^^^^^^^^^^^^^^^^^!89863^1508^1^第二轮^20141017010000^0^波斯皮西^波斯皮西^^^穆雷 ^梅利 ^^^^^^^^^^^^^^^^^^^^^^^^^^^!89864^1508^3^八强^20141016203000^0^卡斯^C·卡斯^科赫尔斯雷伯^P·科爾史裏貝爾^德拉干扎^德拉幹紮^迈尔格亚^F·迈尔格亚^^^^^^^^^^^^^^^^^^^^^^^^^!89897^1508^3^八强^20141017003000^0^贝格曼^貝格曼^J-诺尔斯^J·路维尔^菲斯登伯格^M·菲斯登伯格^马雷洛^馬雷洛^^^^^^^^^^^^^^^^^^^^^^^^^!89896^1508^3^八强^20141017023000^0^S·冈萨雷斯^S·冈萨雷斯^罗索尔^L.羅素^加·洛佩兹^G·加西亞洛佩茲^奥思瓦^奧思瓦^^^^^^^^^^^^^^^^^^^^^^^^^!89895^1508^3^八强^20141017030000^0^梅尔泽^J·邁澤爾^普兹斯内尔^P·普茲斯內爾^帕维奇^帕維奇^A. 沙亚^A·沙亚^^^^^^^^^^^^^^^^^^^^^^^^^";
		AnalyseQiutanJIshiIBiFen analyseQiutanJIshiIBiFen = new AnalyseQiutanJIshiIBiFen();
//		analyseQiutanJIshiIBiFen.analyse(footballData, AnalyseType.football);
		 analyseQiutanJIshiIBiFen.analyse(basketData,AnalyseType.basketball);
		// analyseQiutanJIshiIBiFen.analyse(tennisData,AnalyseType.tennis);
	}

	protected List analyse(String response, AnalyseType type) {
		String s1 = response;
		ArrayList arraylist = new ArrayList();
		boolean s = true;
		g gg = new g();

		f leagueDataF = new f();
		String as[] = s1.split("\\$\\$", -1);
		// int i1 = 0;
		// while(i1 < as.length){
		String leagueData[] = as[1].split("\\!", -1);
		leagueDataF.a(leagueData);
		String bifenData[] = as[1].split("\\!", -1);
		gg.a(type, bifenData, leagueDataF, false, false, false);
		// gg.a1(as1, null);
		// i1++;
		// }

		return arraylist;
	}

	public List<QtMatchBaseModel> analyseJishiBifen(String responseStr) {
		List<QtMatchBaseModel> qtMatchBaseModels = null;
		if (responseStr != null) {
			qtMatchBaseModels = new ArrayList<>();
			String as[] = responseStr.split("\\$\\$", -1);
			if (as.length >= 2) {
				String bifenData[] = as[1].split("\\!", -1);
				if (bifenData.length > 0) {
					for (String bifen : bifenData) {
						String matchData[] = bifen.split("\\^", -1);
						if (matchData.length >= 24) {
							QtMatchBaseModel qtMatchBaseModel = new QtMatchBaseModel();
							qtMatchBaseModel.setBsId(matchData[0]);
							qtMatchBaseModel.setLeagueId(matchData[1]);
							qtMatchBaseModel.setMatchStatus(ValueConvertUtil.safeInteger(matchData[2]));
							qtMatchBaseModel.setMatchTime(DateFormateUtil.toDate("yyyyMMddHHmmss", matchData[3]));
							qtMatchBaseModel.setHalfStartTime(DateFormateUtil.toDate("yyyyMMddHHmmss", matchData[4]));
							qtMatchBaseModel.setHomeTeamName(matchData[5]);
							qtMatchBaseModel.setGuestTeamId(matchData[6]);
							qtMatchBaseModel.setHomeTeamId(matchData[5]);
							qtMatchBaseModel.setGuestTeamName(matchData[6]);
							qtMatchBaseModel.setHomeTeamScore(toConvertNotNullIntegerValue(matchData[7]));
							qtMatchBaseModel.setGuestTeamScore(toConvertNotNullIntegerValue(matchData[8]));
							qtMatchBaseModel.setHomeTeamHalfScore(toConvertNotNullIntegerValue(matchData[9]));
							qtMatchBaseModel.setGuestTeamHalfScore(toConvertNotNullIntegerValue(matchData[10]));
							qtMatchBaseModel.setHomeTeamRed(toConvertNotNullIntegerValue(matchData[11]));
							qtMatchBaseModel.setGuestTeamRed(toConvertNotNullIntegerValue(matchData[12]));
							qtMatchBaseModel.setHomeTeamYellow(toConvertNotNullIntegerValue(matchData[13]));
							qtMatchBaseModel.setGuestTeamYellow(toConvertNotNullIntegerValue(matchData[14]));
							qtMatchBaseModel.setHandiCap(toConvertNotNullDoubleValue(matchData[15]));
							qtMatchBaseModel.setLordOdds(toConvertNotNullDoubleValue(matchData[16]));
							qtMatchBaseModel.setFlatOdds(toConvertNotNullDoubleValue(matchData[17]));
							qtMatchBaseModel.setNegativeOdds(toConvertNotNullDoubleValue(matchData[18]));
							qtMatchBaseModel.setHomeTeamPosition(toConvertNotNullIntegerValue(matchData[19]));
							qtMatchBaseModel.setGuestTeamPosition(toConvertNotNullIntegerValue(matchData[20]));
							qtMatchBaseModel.setTelevison(matchData[23]);
							qtMatchBaseModel.setSource(1);
							qtMatchBaseModel.setProcessStatus(0);
							qtMatchBaseModels.add(qtMatchBaseModel);
						}
					}
				}
			}

		}
		return qtMatchBaseModels;
	}


	public List<BasketBallMatchModel> analyseBasketBallJishiBifen(
			String responseStr) {
		List<BasketBallMatchModel> ballMatchModels = new ArrayList<>();
		String s1[] = responseStr.split("\\$\\$",-1);
		if(s1.length>=2){
			//解析联赛数据
			String league[] = s1[0].split("!");
			Map<String, String> leagueInfoMap = new HashMap<String, String>();
			int i = 0;
			while(i<league.length){
				String[] a2 = league[i].split("\\^");
				if(a2.length>=2){
					leagueInfoMap.put(a2[1], a2[0]);
				}
				i++;
			}
			String as[] = s1[1].split("\\!",-1);
			i=0;
			while(i<as.length){
				String oneMatchArray[] = as[i].split("\\^",-1);
				if(oneMatchArray.length>=25){
					BasketBallMatchModel ballMatchModel = new BasketBallMatchModel();
					ballMatchModel.setBsId(oneMatchArray[0]);
					ballMatchModel.setColor(oneMatchArray[2]);
					ballMatchModel.setLeagueId(oneMatchArray[1]);
					if(oneMatchArray[1]!=null){
						ballMatchModel.setName(leagueInfoMap.get(oneMatchArray[1]));
					}
					ballMatchModel.setMatchTime(DateFormateUtil.toDate("yyyyMMddHHmmss", oneMatchArray[3]));
					ballMatchModel.setMatchState(ValueConvertUtil.safeInteger(oneMatchArray[4]));
					ballMatchModel.setRemainTime(oneMatchArray[5]);
					ballMatchModel.setHomeTeam(oneMatchArray[6]);
					ballMatchModel.setGuestTeam(oneMatchArray[7]);
					ballMatchModel.setHomeScore(toConvertNotNullIntegerValue(oneMatchArray[8]));
					ballMatchModel.setGuestScore(toConvertNotNullIntegerValue(oneMatchArray[9]));
					ballMatchModel.setLetBallNum(toConvertNotNullIntegerValue(oneMatchArray[10]));
					ballMatchModel.setExplainContent(oneMatchArray[13]);
					ballMatchModel.setJingcaiId(oneMatchArray[14]);
					ballMatchModel.setHomeOne(toConvertNotNullIntegerValue(oneMatchArray[15]));
					ballMatchModel.setGuestOne(toConvertNotNullIntegerValue(oneMatchArray[16]));
					ballMatchModel.setHomeTwo(toConvertNotNullIntegerValue(oneMatchArray[17]));
					ballMatchModel.setGuestTwo(toConvertNotNullIntegerValue(oneMatchArray[18]));
					ballMatchModel.setHomeThree(toConvertNotNullIntegerValue(oneMatchArray[19]));
					ballMatchModel.setGuestThree(toConvertNotNullIntegerValue(oneMatchArray[20]));
					ballMatchModel.setHomeFour(toConvertNotNullIntegerValue(oneMatchArray[21]));
					ballMatchModel.setGuestFour(toConvertNotNullIntegerValue(oneMatchArray[22]));
					ballMatchModel.setHomeAddTime1(toConvertNotNullIntegerValue(oneMatchArray[23]));
					ballMatchModel.setGuestAddTime1(toConvertNotNullIntegerValue(oneMatchArray[24]));
					ballMatchModel.setSource(1);
					ballMatchModel.setProcessStatus(0);
					ballMatchModels.add(ballMatchModel);
				}
				i++;
			}
		}
		return ballMatchModels;
	}
	
	private int toConvertNotNullIntegerValue(String integerValue) {
		// TODO Auto-generated method stub
		Integer value = ValueConvertUtil.safeInteger(integerValue);
		
		return value==null?0:value;
	}

	private Double toConvertNotNullDoubleValue(String doubleValue) {
		Double double1 = ValueConvertUtil.safeDouble(doubleValue);
		return double1==null?0.0:double1;
	}

	public List<QtMatchBaseModel> analyseJingcaiJishiBifen(String responseStr) {
		List<QtMatchBaseModel> qtMatchBaseModels = null;
		if (responseStr != null) {
			qtMatchBaseModels = new ArrayList<>();
			String as[] = responseStr.split("\\$\\$", -1);
			if (as.length >= 3) {
				String bifenData[] = as[2].split("\\!", -1);
				if (bifenData.length > 0) {
					for (String bifen : bifenData) {
						String matchData[] = bifen.split("\\^", -1);
						if (matchData.length >= 24) {
							QtMatchBaseModel qtMatchBaseModel = new QtMatchBaseModel();
							qtMatchBaseModel.setBsId(matchData[0]);
							qtMatchBaseModel.setLeagueId(matchData[1]);
							qtMatchBaseModel.setMatchStatus(ValueConvertUtil.safeInteger(matchData[2]));
							qtMatchBaseModel.setMatchTime(DateFormateUtil.toDate("yyyyMMddHHmmss", matchData[3]));
							qtMatchBaseModel.setHalfStartTime(DateFormateUtil.toDate("yyyyMMddHHmmss", matchData[4]));
							qtMatchBaseModel.setHomeTeamName(matchData[5]);
							qtMatchBaseModel.setGuestTeamId(matchData[6]);
							qtMatchBaseModel.setHomeTeamId(matchData[5]);
							qtMatchBaseModel.setGuestTeamName(matchData[6]);
							qtMatchBaseModel.setHomeTeamScore(toConvertNotNullIntegerValue(matchData[7]));
							qtMatchBaseModel.setGuestTeamScore(toConvertNotNullIntegerValue(matchData[8]));
							qtMatchBaseModel.setHomeTeamHalfScore(toConvertNotNullIntegerValue(matchData[9]));
							qtMatchBaseModel.setGuestTeamHalfScore(toConvertNotNullIntegerValue(matchData[10]));
							qtMatchBaseModel.setHomeTeamRed(toConvertNotNullIntegerValue(matchData[11]));
							qtMatchBaseModel.setGuestTeamRed(toConvertNotNullIntegerValue(matchData[12]));
							qtMatchBaseModel.setHomeTeamYellow(toConvertNotNullIntegerValue(matchData[13]));
							qtMatchBaseModel.setGuestTeamYellow(toConvertNotNullIntegerValue(matchData[14]));
							qtMatchBaseModel.setHandiCap(toConvertNotNullDoubleValue(matchData[15]));
							qtMatchBaseModel.setJingcaiId(matchData[16]);
							if(qtMatchBaseModel.getMatchStatus()!=null&&qtMatchBaseModel.getMatchStatus()==2){ //中场时获取半场结束时间
								qtMatchBaseModel.setHalfEndTime(new Date());
							}
							qtMatchBaseModel.setHomeTeamPosition(toConvertNotNullIntegerValue(matchData[19]));
							qtMatchBaseModel.setGuestTeamPosition(toConvertNotNullIntegerValue(matchData[20]));
							if(StringUtils.isNotBlank(matchData[21])){
								String[] live = matchData[21].split(";");
								if(live.length>=5){
									String[] result = live[0].split(",");
									String liveContent = "";
									liveContent = calResult(result, liveContent);
									result = live[2].split(",");
									if(result.length>=2){
										liveContent += ","+result[0]+"20分钟["+result[1]+"]";
									}
									liveContent += ",点球["+live[3]+"]";
									if(StringUtils.equals("1", live[4])){
										liveContent +=","+matchData[5]+"赢";
									}else if(StringUtils.equals("0", live[4])){
										liveContent +=","+matchData[6]+"赢";
									}
									qtMatchBaseModel.setLiveContent(liveContent);
								}
							}
							qtMatchBaseModel.setTelevison(matchData[23]);
							qtMatchBaseModel.setSource(1);
							qtMatchBaseModel.setProcessStatus(0);
							qtMatchBaseModels.add(qtMatchBaseModel);
						}
					}
				}
			}

		}
		return qtMatchBaseModels;
	}

	private String calResult(String[] result, String liveContent) {
		if(result.length>=2){
			liveContent += result[0]+"分钟["+result[1]+"]";
		}
		return liveContent;
	}

	@SuppressWarnings("rawtypes")
	public List<QtMatchBaseModel> analyseQtMatch(String responseStr) {
		List<QtMatchBaseModel> qtMatchBaseModels = null;
		if (responseStr != null) {
			qtMatchBaseModels = new ArrayList<>();
			JSONObject jsonObject = JSONObject.fromObject(responseStr);
			JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("schedulelist");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jo = jsonArray.getJSONObject(i);
				QtMatchBaseModel qtMatchBaseModel = new QtMatchBaseModel();
				qtMatchBaseModel.setBsId(jo.getString("scheduleid"));
				qtMatchBaseModel.setLeagueId(jo.getString("sclass"));
				qtMatchBaseModel.setMatchTime(DateFormateUtil.toDate("yyyyMMddHHmmss", jo.getString("matchtime")));
				qtMatchBaseModel.setHomeTeamName(jo.getString("hometeam"));
				qtMatchBaseModel.setGuestTeamName(jo.getString("guestteam"));
				qtMatchBaseModel.setColor(jo.getString("color"));
				qtMatchBaseModel.setJingcaiId(jo.getString("matchid"));
				qtMatchBaseModels.add(qtMatchBaseModel);
			}
		}
		return qtMatchBaseModels;
	}
}
