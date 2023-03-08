/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80031 (8.0.31)
 Source Host           : localhost:3306
 Source Schema         : lifediary

 Target Server Type    : MySQL
 Target Server Version : 80031 (8.0.31)
 File Encoding         : 65001

 Date: 08/03/2023 18:08:56
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for diary
-- ----------------------------
DROP TABLE IF EXISTS `diary`;
CREATE TABLE `diary`
(
    `id`          int                                                           NOT NULL AUTO_INCREMENT COMMENT '主键',
    `title`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '日记标题',
    `content`     longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '日记正文',
    `type`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '日记分类',
    `use_id`      int                                                           NOT NULL COMMENT '用户id',
    `status`      int                                                           NOT NULL DEFAULT 0 COMMENT '状态',
    `create_time` datetime                                                      NOT NULL COMMENT '发布时间',
    `update_time` datetime NULL DEFAULT NULL COMMENT '修改日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of diary
-- ----------------------------
INSERT INTO `diary`
VALUES (1, '香菜',
        '<p><strong>今天是2月24日，世界讨厌香菜日。</strong></p><p>&nbsp;</p><p>据百度百科介绍——据非官方统计，全球大约有15%的人不吃香菜，也就是说，全球每100人，有15个不喜欢吃香菜，因此在每年2月24日，他们都会举行一个“世界讨厌香菜日”。</p><p>&nbsp;</p><p>我就是那100个人里面的15个人的其中一个了。</p><p>&nbsp;</p><p>网友说：香菜多好吃啊！为什么要讨厌</p><p>&nbsp;</p><p>网友Heather-Tang说: 这个我会，不吃香菜的人是11号染色体中多了一个基因，是嗅觉受体，导致这类人对醛类物质敏感，这类人会觉得香菜闻起来是臭虫的味道吃起来是肥皂的味道</p><p>&nbsp;</p><p>嗯，真的没办法接受那个味道，太臭了！</p>',
        '遇见生活', 1, 0, '2020-02-24 16:26:01', NULL);
INSERT INTO `diary`
VALUES (2, '观影感',
        '<p><span style=\"background-color: rgb(255, 255, 255); color: rgb(51, 51, 51);\">《人鱼的旋律》昨天看完了，今天打开了《keroro军曹》第一季，总算是弥补童年的遗憾了。小学那阵子放学晚了，总是只看到一小部分，从没看完一整部，现在可以一次性看个够了！粤语配音就是温馨！</span></p>',
        '遇见生活', 1, 0, '2020-04-30 16:28:28', NULL);
INSERT INTO `diary`
VALUES (3, '歌手',
        '<p>心血来潮地打开了古早女团Sugar的打歌舞台Shine，才发现当年我很喜欢的那句歌词原来是黄静茵唱的，而不是我以为的亚由美。于是，又后知后觉地知道了“退团后转做演员的黄静茵整容了”，难怪我完全没认出来。还是女团时期的她长得好看，灵气十足，很有识辨度，整容后灵气全无，可惜了......</p><p>&nbsp;</p><p>原来啊，我当年第一眼看中的甜妹就是黄静茵，无论是视觉还是听觉上，吸引我的，由此至终都是黄静茵。她的长相真的长在我的审美上，眼睛很灵动，尤其是那对酒窝，特别甜。</p><p>&nbsp;</p><p>B站和小红书的网友都一致认为黄静茵本来就很美，姐姐怎么那么想不开去整容了？！她原装的脸真的好好看！清纯、灵动、元气满满，可惜啊可惜！</p><p>&nbsp;</p><p>关于她的整容，网友是这么说的，“那会不流行她那种长相，她才开始整的”、“经济公司要整的欧尼为了演员梦整的”、“可能韩国人不喜欢这一挂的”</p><p>&nbsp;</p><p>可是我真的好喜欢她这样的长相，超级无敌灵动漂亮！韩国人不懂得欣赏啊！</p>',
        '遇见惊艳', 1, 0, '2020-05-01 12:33:50', NULL);
INSERT INTO `diary`
VALUES (4, '歌唱家',
        '<p><img src=\"http://localhost:8081/static/upload/diary/7/b2b48a11-26c9-4a0b-a33c-39e84fe1eeb1/fc7ed617-bf3d-4277-9adc-41e4b9831b95.jpg\" style=\"cursor: nesw-resize;\" width=\"215\">反复看Sugar的打歌舞台Shine，完完全全被黄静茵的美给征服到！我真的好吃她的颜！她真的好美！那种美，不是妆容的美，不是服饰衬托的美，而是由内而外的美：青春、活力、眼神清澈，笑容灿烂，我愿用世间一切美好的词语形容她！</p><p>&nbsp;</p><p>2003年，她才18岁，举手投足都散发着青春和元气，美到我移不开眼睛！就连她转身我都觉得好美！我真的疯了！</p><p>&nbsp;</p><p>关键是她不但人长得美，她的音色也绝美！在我第一次听她唱的那句“Baby please never break my heart”，耳朵就完全被她的音色给迷倒。即使不是她唱歌的part，我的视线也一直追随着她，她笑起来真的好好看！</p><p>&nbsp;</p><p>要是她当初没有退团多好，要是她没有整容多好，然后用她甜美的脸庞和甜美的歌声，在舞台留下更多珍贵的影像。</p>',
        '遇见惊艳', 1, 0, '2020-05-01 16:32:24', NULL);
INSERT INTO `diary`
VALUES (5, '想念螺蛳粉',
        '<p>下午去了一趟银行办理限额的事情，完事了去了零食店。那里店面大，但想吃的东西没有特别多，没有找到我想吃的布丁果冻，最后买了一罐藕粉和鹿角巷。明天早上要早起，试试那藕粉好不好吃。</p><p>&nbsp;</p><p>我的螺蛳粉海报进度90%，今天依然是想吃螺蛳粉的一天。</p>',
        '遇见生活', 1, 0, '2022-05-03 16:35:17', NULL);
INSERT INTO `diary`
VALUES (6, '最求',
        '<p>今天看到一帖子，用一些词语形容INFP，“好奇心、创造力、爱反省、浪漫主义”。很是准确呢！</p><p>&nbsp;</p><p>在正规的官网上测试，我是INFP，而一年前在国内某个知名的专业的公众号做的测试，我是ISFP。我打算几个月之后再去官网测测，不是觉得不准，而是看看那个时候的自己，不同的选项会不会有不一样的结果。</p><p>&nbsp;</p><p>之前一个帖子，博主写了她对各种MBTI各类人格的第一印象。关于INFP，她写道“天生破碎但永恒美”，得到底下一众INFP的高度认可。</p><p>&nbsp;</p><p>“天生破碎但永恒美”，确实是这样，不完美的人，总是在追求永恒的美。</p>',
        '遇见自己', 1, 0, '2020-05-06 16:36:24', NULL);
INSERT INTO `diary`
VALUES (7, '梦',
        '<p>两天前的晚上，也就是2020年12月30日，我刷到了一条小红书，上面配图一张热腾腾的螺蛳粉，配文“祝我的2023像这碗螺蛳粉——丰富、热烈、古怪，而自得其乐！干杯！”</p><p>&nbsp;</p><p>于是我留言道：“干碗！祝我们的2023都像螺蛳粉一样，丰富、热烈、古怪且自得其乐！”丰富、热烈、古怪且自得其乐，真是很好的期望呢！想吃螺蛳粉了。</p><p>前些天看中的爱心背心毛衣，终于找到一家，有我能穿的码了！今天下单了，期待到货！我对好看的东西，真的没有一点抵抗力！</p><p>之前在梦里见到的那件兔子衣服，我在一家店铺有看到，和我梦中见到的很是相似。但我没有下单，因为它还不能百分百地击中我的灵魂。昨晚在一家店铺看到一条裙子，是紫色和松石绿的配色，很有设计感，特别好看。果然，有设计感加持的衣服就是好看。只可惜依然是小码童装，无法入手。</p>',
        '遇见梦境', 1, 0, '2020-12-30 16:37:23', NULL);
INSERT INTO `diary`
VALUES (8, '重温电影',
        '<p>2023年的第一天，有被惊讶到。因为 土屋太凤和片寄凉太 官宣了，两人宣布结婚并且迎来了新生命。认识两人，当然是因为两人的代表作《哥哥太爱我了怎么办》，而两人也是因为这部戏而结缘，秘密拍了五年恋爱。</p><p>&nbsp;</p><p>当初我还是刷微博的时候看到了短片，被两人的颜值吸引而看的呢。当时还觉得两人很是般配，没想到两人因这部剧而结缘，从戏里走到了戏外。还是拍了五年呢，缘分太神奇了。</p><p>&nbsp;</p><p>当初看完剧，有一段时间很迷片寄凉太的颜值。片寄凉太那个时期真的好帅！简直是从漫画里走出来的人物，只可惜他花期太短了。今天在小红书里面看到粉丝评论说，他本来就不是瘦的类型，只是恰巧拍《哥哥太爱我了怎么办》的时候减肥了，是他最瘦的时候罢了。</p><p>&nbsp;</p><p>当初看完剧，短暂地磕了一下两人，但时间不长。总的来说，真的好惊喜，没想到我2017年磕的CP成真了。嗯，世间一切，皆有可能。</p><p>&nbsp;</p><p>想要重温《哥哥太爱我了怎么办》了。</p><p><img src=\"http://localhost:8081/static/upload/diary/7/69e1f468-5491-4d6a-9480-62a380a70255/d002c0bc-e8c5-475b-a49a-108ae8b388a5.jpg\"></p>',
        '遇见生活', 1, 0, '2023-01-01 16:39:17', NULL);
INSERT INTO `diary`
VALUES (9, '天气与生活',
        '<p><span style=\"background-color: rgb(255, 255, 255); color: rgb(51, 51, 51);\">我常常觉得，我应该生在热带地区。广东10-20°的天气，我都觉得冷极了。手是冰冻的，脚也是冰冻的。如果我生在热带地区，我就可以省下好多钱了。我不用买羽绒服，不用买棉裤，不用买毛衣，也不用买围巾。然后，我就可以在夏天吃火锅，冬天吃冰淇淋了。</span><img src=\"http://localhost:8081/static/upload/diary/7/aaf22120-60d6-4f81-af99-1c606506f15b/35c5faa8-49c2-496a-afc0-8bb61eb56678.jpg\"></p>',
        '遇见生活', 1, 0, '2023-01-02 16:40:49', NULL);
INSERT INTO `diary`
VALUES (10, '药药',
        '<p>医生开的药💊💊💊，治咳嗽的痰有效。对我的感冒没有效果啊，鼻子好痛，因为流鼻水擦鼻涕。</p><p>&nbsp;</p><p>治咳嗽的痰的药叫“乙酰半胱氨酸颗粒”，味道是甜的，非常甜。很像橙子味，下午喝的时候，吃了汉堡包软糖。这两个的橙子味很相似，但因为前者是药，后者是糖，味道差了不是一星半点。心理作用真的很强大。</p>',
        '遇见生活', 1, 0, '2023-01-03 16:57:44', NULL);
INSERT INTO `diary`
VALUES (11, '观影',
        '<p>我花了不到一个下午的时间，把《爱丽丝梦游仙境》看完了。这应该可以载入“我阅读时间最快”的历史里。</p><p><img src=\"http://localhost:8081/static/upload/diary/7/7670e104-1aff-4259-906c-06c31cf6f596/11f4466f-b740-4cb4-ad2c-6d9e0bb3f5e2.jpg\"></p><p>&nbsp;</p><p>书本并不薄，我看的是插图版的，因为是儿童文学，翻译得很通俗易懂，没有晦涩难懂的深奥句子，所以阅读起来很轻松，阅读时间比我想象得要短。这过程中，我还刷了一下小红书，吃了一颗汉堡橡皮糖，去逗了一会小侄子，吃了一块地瓜红糖饼。</p><p>&nbsp;</p><p>下了雨，天气更加冷了。我真的很不喜欢冬天，真的太冷了。晚上洗澡都要勇气。沐浴露我都是在掌心里搓暖了，才敢抹上皮肤。涂身体乳的时候，都要速战速决。</p><p>&nbsp;</p><p>今晚要盖三张被子了，今晚要在十一点前睡觉，希望自己做得到。</p>',
        '遇见生活', 1, 0, '2023-01-04 17:09:59', NULL);
INSERT INTO `diary`
VALUES (12, '缘分未到',
        '<p>阿鱼在微信上问我，什么时候可以看到我穿婚纱，我的男朋友呢？然后我很机智地回答：“梦里吧（看到我穿婚纱）。我的男朋友是螺蛳粉。螺蛳粉，我的最爱。”</p><p>&nbsp;</p><p>爸爸让我明天开车接他去Chen cun，一个亲戚回去送喜饼。然后我再次深深地感受到，我真的无办法步入婚姻。能让我上头的，只有螺蛳粉了。</p><p>&nbsp;</p><p>其实也不仅仅是螺蛳粉，去年让我心动的那个男生啊，和他确实是没有缘分。</p>',
        '遇见自己', 1, 0, '2023-01-05 17:16:00', NULL);
INSERT INTO `diary`
VALUES (13, '书',
        '<p>年少时看的一本书，多年后的某天突然想要重温。《八十八夜》便是这本书，一本书洋溢着清新气息的书。</p><p>第一次看《八十八夜》这本书，是高一。那是十一年前的事情了，当时在学校的图书馆发现了这本书，名字很特别。我想，吸引我的仅仅是名字，还有书中的故事。</p><p>“10岁移居上海后开始学习音乐的日本少年晋华，因为一杯清茶，认识了天才少女可人。但由于他身边还有胜似妹妹一样的少女阿宝，一段曲折而浪漫的故事就此在三人之间展开。”这便是《八十八夜》的故事简介。</p><p>“作者行文优美，描写感情真挚，并将音乐、爱情、影像巧妙地糅合在一起，是一部清新动人、形式新颖的作品。”这是网上对这本书的评价，我想，这也是让我想重温的原因之一。</p><p>去了一趟图书馆，再次在书架上看到《石榴树上结樱桃》，再次感叹书名真是好听，可惜不是我喜欢看的类型。在找书的过程中看到2015认识的一个作家，她的新作品和旧作品陈列在上，唯独没有我想看的那本。可是，那是一本作者只写了几章的书，一本作者不会再继续写下去的书。</p><p>图书馆里没有《八十八夜》这本书，网上也找不到可阅读的资源，这应该就是我和它的缘分了。时隔十一年，它成为我藏书的缘分。</p><p><img src=\"http://localhost:8081/static/upload/diary/7/f92e93b7-62fa-4ff0-b568-ef3a9d6cd145/f5ae8689-530e-426c-8b4b-cc626014052d.jpg\" style=\"cursor: nwse-resize;\" width=\"240\"></p>',
        '遇见时光', 1, 0, '2023-01-06 17:20:14', NULL);

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource`
(
    `id`          int                                                           NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`     int                                                           NOT NULL COMMENT '用户ID',
    `diary_id`    int NULL DEFAULT NULL COMMENT '日记id',
    `type`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '资源类型',
    `address`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '存放位置',
    `status`      int NULL DEFAULT NULL COMMENT '状态',
    `create_time` datetime                                                      NOT NULL COMMENT '创建时间',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 156 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource`
VALUES (1, 1, 4, 'image/jpeg',
        'static/upload/diary/7/b2b48a11-26c9-4a0b-a33c-39e84fe1eeb1/fc7ed617-bf3d-4277-9adc-41e4b9831b95.jpg', 1,
        '2023-03-08 16:31:19', '2023-03-08 16:32:24');
INSERT INTO `resource`
VALUES (2, 1, 10, 'image/jpeg',
        'static/upload/diary/7/69e1f468-5491-4d6a-9480-62a380a70255/d002c0bc-e8c5-475b-a49a-108ae8b388a5.jpg', 1,
        '2023-03-08 16:39:12', '2023-03-08 16:39:17');
INSERT INTO `resource`
VALUES (3, 1, 11, 'image/jpeg',
        'static/upload/diary/7/aaf22120-60d6-4f81-af99-1c606506f15b/35c5faa8-49c2-496a-afc0-8bb61eb56678.jpg', 1,
        '2023-03-08 16:40:46', '2023-03-08 16:40:49');
INSERT INTO `resource`
VALUES (4, 1, 12, 'image/jpeg',
        'static/upload/diary/7/7670e104-1aff-4259-906c-06c31cf6f596/11f4466f-b740-4cb4-ad2c-6d9e0bb3f5e2.jpg', 1,
        '2023-03-08 17:09:52', '2023-03-08 17:09:59');
INSERT INTO `resource`
VALUES (5, 1, 13, 'image/jpeg',
        'static/upload/diary/7/f92e93b7-62fa-4ff0-b568-ef3a9d6cd145/f5ae8689-530e-426c-8b4b-cc626014052d.jpg', 1,
        '2023-03-08 17:19:58', '2023-03-08 17:20:14');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`          int                                                           NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
    `account`     int                                                           NOT NULL COMMENT '账户',
    `password`    varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '密码',
    `icon`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
    `status`      int                                                           NOT NULL DEFAULT 0 COMMENT '状态',
    `create_time` datetime                                                      NOT NULL COMMENT '创建时间',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user`
VALUES (1, '春晓', 123456, '123456', 'static/upload/icon/cf2b3813-cc5c-427d-a749-80f834241e95.jpg', 0,
        '2020-02-01 16:06:19', '2023-03-08 16:08:31');

SET
FOREIGN_KEY_CHECKS = 1;
