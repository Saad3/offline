-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 20, 2016 at 12:44 PM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `offline`
--

-- --------------------------------------------------------

--
-- Table structure for table `cities`
--

CREATE TABLE `cities` (
  `id` int(4) NOT NULL,
  `parent_id` varchar(4) DEFAULT NULL,
  `english` varchar(27) DEFAULT NULL,
  `arabic` varchar(24) DEFAULT NULL,
  `latitude` varchar(6) DEFAULT NULL,
  `longitude` varchar(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cities`
--

INSERT INTO `cities` (`id`, `parent_id`, `english`, `arabic`, `latitude`, `longitude`) VALUES
(1, '1', 'Riyadh', 'الرياض', '24.65', '46.77'),
(2, '2', 'Mecca', 'مكة المكرمة', '21.43', '39.82'),
(3, '3', 'Sarqiyah', 'المنطقة الشرقية', '', ''),
(4, '4', 'Qasim', 'القصيم', '', ''),
(5, '5', 'Hudud-Samaliyah', 'الحدود الشمالية', '', ''),
(6, '6', 'Asir', 'عسير', '', ''),
(7, '7', 'Jizan', 'جيزان', '16.9', '42.55'),
(8, '8', 'Bahah', 'الباحه', '20.02', '41.47'),
(9, '9', 'Tabuk', 'تبوك', '28.39', '36.57'),
(10, '10', 'Jawf', 'الجوف', '29.81', '39.87'),
(11, '11', 'hail', 'حائل', '', ''),
(12, '12', 'Madinah', 'المدينة المنورة', '24.48', '39.59'),
(13, '13', 'Najran', 'نجران', '17.5', '44.13'),
(14, '6', 'Abha', 'ابها', '18.23', '42.5'),
(15, '7', 'Abu Aris', 'ابو عريش', '16.97', '42.83'),
(16, '7', 'Abu Sala', 'ابو السلع', '', ''),
(17, '7', 'Dair', 'الدائر', '', ''),
(18, '3', 'Dammam', 'الدمام', '26.43', '50.1'),
(19, '7', 'Darb', 'الدرب', '17.73', '42.25'),
(20, '1', 'Dawadimi', 'الدوادمى', '24.49', '44.38'),
(21, '3', 'Dibiyah', 'الذيبيه', '', ''),
(22, '1', 'Dilam', 'الدلم', '24', '47.17'),
(23, '1', 'Diriyah', 'الدرعية', '24.75', '46.57'),
(24, '1', 'Dubayah', 'الضبيعه', '24.1', '47.17'),
(25, '1', 'Afif', 'عفيف', '23.92', '42.93'),
(26, '7', 'Ahad Masarihah', 'احد المسارحه', '16.71', '42.95'),
(27, '6', 'Ahad Rafidah', 'احد رفيده', '18.2', '42.8'),
(28, '7', 'Aliyah wHaDra', 'العاليه والخضراء', '16.58', '42.96'),
(29, '8', 'Aqiq', 'العقيق', '20.27', '41.67'),
(30, '1', 'Artawiyah', 'الارطاويه', '26.51', '45.34'),
(31, '3', 'Awamiyah', 'العواميه', '26.59', '49.99'),
(32, '3', 'Awjam', 'الاوجام', '26.56', '49.94'),
(33, '9', 'Bad', 'البدع', '28.49', '35.01'),
(34, '4', 'Badai', 'البدائع', '27.45', '42.98'),
(35, '1', 'Badi Samali', 'البديع الشمالى', '22.02', '46.58'),
(36, '7', 'Badi wQurfi', 'البديع والقرفي', '16.99', '42.77'),
(37, '3', 'Batha', 'البطحاء', '26.39', '49.98'),
(38, '3', 'Battaliyah', 'البطاليه', '25.43', '49.63'),
(39, '4', 'Bukayriyah', 'البكيريه', '26.0', '43.66'),
(40, '3', 'FuDul', 'الفضول', '26.98', '49.17'),
(41, '1', 'Gat', 'الغاط', '26.03', '44.95'),
(42, '4', 'Habra', 'الخبراء', '26.05', '43.55'),
(43, '2', 'Hada', 'الهدا', '21.37', '40.28'),
(44, '10', 'haditah Qadimah', 'الحديثه القديمه', '31.46', '37.16'),
(45, '3', 'Hafji', 'الخفجى', '28.42', '48.51'),
(46, '11', 'hait', 'الحائط', '25.97', '40.45'),
(47, '12', 'hanakiyah', 'الحناكيه', '24.88', '40.52'),
(48, '1', 'hariq', 'الحريق', '23.63', '46.51'),
(49, '1', 'Harj', 'الخرج', '24.0', '47.31'),
(50, '2', 'hawiyah', 'الحويه', '21.44', '40.5'),
(51, '3', 'hawtah', 'الحوطه', '', ''),
(52, '1', 'Hayatim', 'الهياثم', '24.17', '47.23'),
(53, '1', 'hayir', 'الحاير', '25.79', '45.38'),
(54, '3', 'Hubar', 'الخبر', '26.23', '50.2'),
(55, '3', 'Hufuf', 'الهفوف', '25.35', '49.58'),
(56, '3', 'hulaylah', 'الحليله', '25.43', '49.67'),
(57, '1', 'hulwah', 'الحلوه', '23.45', '46.81'),
(58, '2', 'Hurmah', 'الخرمه', '21.9', '42.05'),
(59, '7', 'husayni', 'الحسينى', '17.0', '42.68'),
(60, '3', 'Huwaylidah', 'الخويلديه', '', ''),
(62, '6', 'Itnayn', 'الاثنين', '18.04', '42.75'),
(63, '3', 'Jafr', 'الجفر', '25.38', '49.72'),
(64, '7', 'Jaradiyah', 'الجراديه', '16.58', '42.91'),
(65, '3', 'Jarwadiyah', 'الجاروديه', '', ''),
(66, '3', 'Jissah', 'الجشه', '', ''),
(67, '3', 'Jissan', 'الجشن', '25.39', '49.75'),
(68, '3', 'Jubayl', 'الجبيل', '27.01', '49.65'),
(69, '3', 'Jubayl', 'الجبيل', '25.4', '49.65'),
(70, '2', 'Jumum', 'الجموم', '21.62', '39.7'),
(71, '3', 'Jurn', 'الجرن', '', ''),
(72, '1', 'Laila', 'ليلى', '22.92', '46.73'),
(73, '2', 'Lays', 'الليش', '', ''),
(74, '7', 'MaDaya', 'المضايا', '16.77', '42.73'),
(75, '12', 'Mahafir', 'المحفر', '', ''),
(76, '8', 'MaHwah', 'المخواه', '19.78', '41.44'),
(77, '6', 'Majaridah', 'المجارده', '19.12', '41.91'),
(78, '1', 'Majmaah', 'المجمعه', '25.9', '45.35'),
(79, '3', 'Mansurah', 'المنصوره', '', ''),
(80, '3', 'Markaz', 'المركز', '25.4', '49.73'),
(81, '13', 'Masaliyah', 'المشعلية', '', ''),
(82, '7', 'Matan', 'المطعن', '17.44', '42.55'),
(83, '4', 'MiDnab', 'المذنب', '25.86', '44.23'),
(84, '8', 'Mindaq', 'المندق', '20.1', '41.28'),
(85, '3', 'Mubarraz', 'المبرز', '25.43', '49.57'),
(86, '3', 'Munayzilah', 'المنيزله', '25.38', '49.67'),
(87, '3', 'Mutayrifi', 'المطيرفى', '25.48', '49.56'),
(88, '2', 'Muwayh', 'المويه الجديد', '22.43', '41.76'),
(89, '1', 'Muzahimiyah', 'المزاحميه', '24.47', '46.28'),
(90, '2', 'Muzaylif', 'المظيلف', '19.53', '41.05'),
(91, '3', 'Qarah', 'القاره', '25.42', '49.67'),
(92, '3', 'Qatif', 'القطيف', '26.52', '50.02'),
(93, '3', 'Qaysumah', 'القيصومه', '28.31', '46.13'),
(94, '3', 'Qudayh', 'القديح', '26.57', '49.99'),
(95, '2', 'QunfuDah', 'القنفذه', '19.13', '41.08'),
(96, '3', 'Qurayn', 'القرين', '25.48', '49.6'),
(97, '10', 'Qurayyat', 'القريات', '31.33', '37.34'),
(98, '1', 'Quwayiyah', 'القويعيه', '24.07', '45.28'),
(99, '2', 'Quz', 'القوز', '18.97', '41.31'),
(100, '12', 'Ula', 'العلا', '26.63', '37.92'),
(101, '6', 'Ulayyah', 'العلاية', '19.61', '41.97'),
(102, '3', 'Umran', 'العمران', '25.42', '49.72'),
(103, '5', 'Uwayqilah', 'العويقيله', '30.36', '42.24'),
(104, '1', 'Uyaynah wa Mazar Ahal', 'العيينة و مزار عها', '24.91', '46.38'),
(105, '3', 'Uyun', 'العيون', '25.61', '49.54'),
(106, '9', 'Wajh', 'الوجه', '26.23', '36.47'),
(107, '3', 'Ank', 'عنك', '26.53', '50.02'),
(108, '3', 'an-Nabiyah', 'النابيه', '26.49', '49.97'),
(109, '6', 'an-Nimas', 'النماص', '19.12', '42.13'),
(110, '3', 'an-Nuayriyah', 'النعيريه', '27.47', '48.47'),
(111, '5', 'Araar', 'عرعر', '30.99', '41.02'),
(112, '4', 'Rass', 'الرس', '25.87', '43.5'),
(113, '3', 'Rumaylah', 'الرميله', '', ''),
(114, '2', 'sahwah', 'الصهوة البعير', '18.95', '41.35'),
(115, '1', 'Sullayyil', 'السليل', '20.46', '45.57'),
(116, '7', 'suqayri', 'الشقيرى', '17.13', '42.82'),
(117, '2', 'taif', 'الطائف', '21.26', '40.38'),
(118, '12', 'tamad', 'الثمد', '', ''),
(119, '3', 'taraf', 'الطرف', '25.36', '49.72'),
(120, '3', 'Tubi', 'التوبى', '26.56', '49.99'),
(121, '7', 'tuwal', 'الطوال', '26.01', '42.39'),
(122, '3', 'Tuwaytir', 'التويثير', '', ''),
(123, '7', 'zabyah', 'الظبيه', '17.12', '42.67'),
(124, '3', 'zahran', 'الظهران', '26.29', '50.16'),
(125, '1', 'Zulfi', 'الزلفى', '26.3', '44.8'),
(126, '12', 'Badr', 'بدر', '23.78', '38.79'),
(127, '2', 'Bahrah', 'بحره', '21.4', '39.45'),
(128, '8', 'Baljursi', 'بلجرشي', '19.86', '41.56'),
(129, '3', 'Bani Man', 'بني معن', '25.38', '49.63'),
(130, '11', 'Baqa Luwaymi', 'بقعاء اللويمى', '27.88', '42.41'),
(131, '9', 'Bir Ibn Hirmas', 'بئر بن هرماس', '28.85', '36.27'),
(132, '7', 'Bisa', 'بيش', '17.37', '42.54'),
(133, '6', 'Bisah', 'بيشه', '20.01', '42.6'),
(134, '3', 'Buqayq', 'بقيق', '25.93', '49.67'),
(135, '4', 'Buraydah', 'بريده', '26.37', '43.97'),
(136, '9', 'Dabba', 'ضباء', '27.35', '35.67'),
(137, '7', 'Damad', 'ضمد', '17.1', '42.78'),
(138, '1', 'Darma', 'ضرما', '24.61', '46.13'),
(139, '6', 'Farah Turayb', 'فرعه طريب', '', ''),
(140, '7', 'Farasan', 'فرسان', '16.7', '42.12'),
(141, '13', 'habawna', 'حبونا', '17.83', '44.02'),
(142, '3', 'hafar Batin', 'حفر البطن', '28.43', '45.96'),
(143, '4', 'Hajrah Aqlat Saqur', 'هجرة عقلة الصقور', '', ''),
(144, '7', 'hakimah', 'حاكمه', '17.02', '42.83'),
(145, '6', 'Hamis Musayt', 'خميس مشيط', '18.31', '42.73'),
(146, '9', 'haql', 'حقل', '29.29', '34.95'),
(147, '1', 'harmah', 'حرمه', '25.92', '45.34'),
(148, '1', 'hawtah Bani Tamim', 'حوطه بنى تميم', '23.49', '46.75'),
(149, '1', 'hawtah Sudayr', 'حوطه سدير', '25.6', '45.61'),
(150, '12', 'Haybar', 'خيبر', '25.75', '39.47'),
(151, '13', 'hayirat Salam', 'حايرة السلم', '', ''),
(152, '1', 'hayy Qattar', 'حى القطار', '', ''),
(153, '3', 'hllat Muhis', 'حله محيش', '26.54', '50'),
(154, '2', 'Hulays', 'خليص', '22.0', '39.32'),
(155, '1', 'huraymila', 'حريملاء', '25.13', '46.12'),
(156, '1', 'Irqah', 'عرقه', '24.69', '46.6'),
(157, '2', 'Jiddah', 'جدة', '21.5', '39.17'),
(158, '3', 'Julayjilah', 'جليجله', '25.5', '49.6'),
(159, '12', 'Mahd Dahab', 'مهد الذهب', '23.5', '40.87'),
(160, '1', 'Marat', 'مرات', '25.07', '45.46'),
(161, '7', 'Masliyah', 'مسليه', '17.47', '42.55'),
(162, '2', 'Masturah', 'مستوره', '', ''),
(163, '7', 'Mizhirah', 'مزهره', '16.83', '42.73'),
(164, '6', 'Muhail', 'محائل', '18.55', '42.05'),
(165, '8', 'Qalwah', 'قلوه', '', ''),
(166, '3', 'Qaryah Ulya', 'قريه العليا', '27.56', '47.7'),
(167, '4', 'Qubbah', 'قبه', '', ''),
(168, '2', 'Rabig', 'رابغ', '22.8', '39.02'),
(169, '5', 'Rafhah', 'رفحة', '29.63', '43.5'),
(170, '3', 'Rahimah', 'رحيمة', '26.7', '50.06'),
(171, '2', 'Ranyah', 'رنيه', '21.27', '42.83'),
(172, '1', 'Rumah', 'رماح', '25.57', '47.16'),
(173, '7', 'sabya', 'صبيا', '17.0', '42.62'),
(174, '3', 'safwa', 'صفوى', '26.65', '49.96'),
(175, '5', 'Sah Ubaydah', 'ساة عبيدة', '', ''),
(176, '1', 'Sajir', 'ساجر', '25.18', '44.6'),
(177, '10', 'Sakakah', 'سكاكة', '29.97', '40.2'),
(178, '7', 'samitah', 'صامطه', '16.6', '42.94'),
(179, '1', 'saqra', 'شقراء', '25.25', '45.25'),
(180, '13', 'sarurah', 'شروره', '17.47', '47.12'),
(181, '3', 'Sayhat', 'سيهات', '26.47', '50.05'),
(182, '11', 'sinana', 'الشنان', '25.81', '43.44'),
(183, '10', 'suwayr', 'صوير', '30.12', '40.39'),
(184, '1', 'tadiq', 'ثادق', '25.3', '45.87'),
(185, '6', 'Tanumah', 'تنومة', '27.1', '44.13'),
(186, '3', 'taqbah', 'الثقبه', '25.32', '49.52'),
(187, '3', 'Tarut', 'تاروت', '26.57', '50.07'),
(188, '6', 'Tatlis', 'تثليش', '19.57', '43.5'),
(189, '9', 'Tayma', 'تيماء', '27.63', '38.48'),
(190, '10', 'tubarjal', 'طبرجل', '30.5', '38.22'),
(191, '1', 'Tumayr', 'تمير', '25.71', '45.87'),
(192, '2', 'Turabah', 'تربه', '21.22', '41.64'),
(193, '5', 'turayf', 'طريف', '31.67', '38.66'),
(194, '2', 'tuwal', 'ثول', '22.27', '39.11'),
(195, '3', 'Umm hammam', 'ام الحمام', '26', '48.81'),
(196, '3', 'Umm Sahik', 'ام الساهك', '26.65', '49.92'),
(197, '9', 'Umm Lajj', 'املج', '25.02', '37.27'),
(198, '4', 'Unayzah', 'عنيزه', '26.1', '43.98'),
(199, '2', 'Usayrah', 'عشيره', '21.76', '40.65'),
(200, '4', 'Uyun Jiwa', 'عيون الجواء', '26.53', '43.69'),
(201, '1', 'Wadi Dawasir', 'وادى الدواسر', '20.47', '44.79'),
(202, '12', 'Yanbu', 'ينبع البحر', '24.09', '38.05'),
(203, '6', 'zahran Janub', 'ظهران الجنوب', '17.66', '43.51');

-- --------------------------------------------------------

--
-- Table structure for table `contain`
--

CREATE TABLE `contain` (
  `item_id` varchar(255) NOT NULL,
  `tag_id` varchar(255) NOT NULL,
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `geotag`
--

CREATE TABLE `geotag` (
  `item_id` varchar(255) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `item_id` varchar(100) CHARACTER SET utf8 NOT NULL,
  `user_id` varchar(100) NOT NULL,
  `textual_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `url_count` int(3) DEFAULT '0',
  `tag_count` int(2) DEFAULT '0',
  `timestamp` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `like`
--

CREATE TABLE `like` (
  `item_id` int(255) NOT NULL,
  `user_id` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `republish`
--

CREATE TABLE `republish` (
  `item_id` int(255) NOT NULL,
  `user_id` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tags`
--

CREATE TABLE `tags` (
  `tag_id` int(11) NOT NULL,
  `keyword` varchar(100) NOT NULL,
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `user_pio` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `states` varchar(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `visual`
--

CREATE TABLE `visual` (
  `item_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `visual_URL` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `contain`
--
ALTER TABLE `contain`
  ADD PRIMARY KEY (`item_id`,`tag_id`),
  ADD UNIQUE KEY `item_id` (`item_id`,`tag_id`),
  ADD UNIQUE KEY `item_id_2` (`item_id`,`tag_id`),
  ADD UNIQUE KEY `item_id_3` (`item_id`,`tag_id`);

--
-- Indexes for table `geotag`
--
ALTER TABLE `geotag`
  ADD PRIMARY KEY (`item_id`);

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`item_id`,`user_id`);

--
-- Indexes for table `like`
--
ALTER TABLE `like`
  ADD PRIMARY KEY (`item_id`,`user_id`);

--
-- Indexes for table `republish`
--
ALTER TABLE `republish`
  ADD PRIMARY KEY (`item_id`,`user_id`);

--
-- Indexes for table `tags`
--
ALTER TABLE `tags`
  ADD PRIMARY KEY (`tag_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tags`
--
ALTER TABLE `tags`
  MODIFY `tag_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=398;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
