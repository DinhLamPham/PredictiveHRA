-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.11-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for table processmining.model
CREATE TABLE IF NOT EXISTS `model` (
  `id` text COLLATE utf8_bin DEFAULT NULL,
  `name` text COLLATE utf8_bin DEFAULT NULL,
  `feature` text COLLATE utf8_bin DEFAULT NULL,
  `predicttype` text COLLATE utf8_bin DEFAULT NULL,
  `stepin` text COLLATE utf8_bin DEFAULT NULL,
  `stepout` text COLLATE utf8_bin DEFAULT NULL,
  `name_to_int_set` text COLLATE utf8_bin DEFAULT NULL,
  `int_to_name_set` text COLLATE utf8_bin DEFAULT NULL,
  `val_accuracy` text COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Dumping data for table processmining.model: ~57 rows (approximately)
DELETE FROM `model`;
/*!40000 ALTER TABLE `model` DISABLE KEYS */;
INSERT INTO `model` (`id`, `name`, `feature`, `predicttype`, `stepin`, `stepout`, `name_to_int_set`, `int_to_name_set`, `val_accuracy`) VALUES
	('review_example_large2feature_Activity2_1.h5', 'review_example_large', '2', 'Activity', '2', '1', 'review_example_large_predict_Activity_2feature_NameToInt.pickle', 'review_example_large_predict_Activity_2feature_IntToName.pickle', '0.7286265419345113'),
	('review_example_large2feature_Activity2_2.h5', 'review_example_large', '2', 'Activity', '2', '2', 'review_example_large_predict_Activity_2feature_NameToInt.pickle', 'review_example_large_predict_Activity_2feature_IntToName.pickle', '0.7248655242417238'),
	('review_example_large2feature_Activity3_1.h5', 'review_example_large', '2', 'Activity', '3', '1', 'review_example_large_predict_Activity_2feature_NameToInt.pickle', 'review_example_large_predict_Activity_2feature_IntToName.pickle', '0.7402896041229335'),
	('review_example_large2feature_Activity3_2.h5', 'review_example_large', '2', 'Activity', '3', '2', 'review_example_large_predict_Activity_2feature_NameToInt.pickle', 'review_example_large_predict_Activity_2feature_IntToName.pickle', '0.7274187236963289'),
	('review_example_large2feature_Activity3_3.h5', 'review_example_large', '2', 'Activity', '3', '3', 'review_example_large_predict_Activity_2feature_NameToInt.pickle', 'review_example_large_predict_Activity_2feature_IntToName.pickle', '0.7153333676064593'),
	('review_example_large2feature_Activity4_1.h5', 'review_example_large', '2', 'Activity', '4', '1', 'review_example_large_predict_Activity_2feature_NameToInt.pickle', 'review_example_large_predict_Activity_2feature_IntToName.pickle', '0.7054921507185572'),
	('review_example_large2feature_Activity4_2.h5', 'review_example_large', '2', 'Activity', '4', '2', 'review_example_large_predict_Activity_2feature_NameToInt.pickle', 'review_example_large_predict_Activity_2feature_IntToName.pickle', '0.6966963483029015'),
	('review_example_large2feature_Activity4_3.h5', 'review_example_large', '2', 'Activity', '4', '3', 'review_example_large_predict_Activity_2feature_NameToInt.pickle', 'review_example_large_predict_Activity_2feature_IntToName.pickle', '0.7023966569858534'),
	('review_example_large2feature_Activity4_4.h5', 'review_example_large', '2', 'Activity', '4', '4', 'review_example_large_predict_Activity_2feature_NameToInt.pickle', 'review_example_large_predict_Activity_2feature_IntToName.pickle', '0.6650321528513078'),
	('review_example_large2feature_Activity5_1.h5', 'review_example_large', '2', 'Activity', '5', '1', 'review_example_large_predict_Activity_2feature_NameToInt.pickle', 'review_example_large_predict_Activity_2feature_IntToName.pickle', '0.6725428789221669'),
	('review_example_large2feature_Activity5_2.h5', 'review_example_large', '2', 'Activity', '5', '2', 'review_example_large_predict_Activity_2feature_NameToInt.pickle', 'review_example_large_predict_Activity_2feature_IntToName.pickle', '0.6976784052195354'),
	('review_example_large2feature_Activity5_3.h5', 'review_example_large', '2', 'Activity', '5', '3', 'review_example_large_predict_Activity_2feature_NameToInt.pickle', 'review_example_large_predict_Activity_2feature_IntToName.pickle', '0.6793031907486545'),
	('review_example_large2feature_Activity5_4.h5', 'review_example_large', '2', 'Activity', '5', '4', 'review_example_large_predict_Activity_2feature_NameToInt.pickle', 'review_example_large_predict_Activity_2feature_IntToName.pickle', '0.6413502898099964'),
	('review_example_large2feature_Performer5_1.h5', 'review_example_large', '2', 'Performer', '5', '1', 'review_example_large_predict_Performer_2feature_NameToInt.pickle', 'review_example_large_predict_Performer_2feature_IntToName.pickle', '0.6685266016032043'),
	('review_example_large2feature_Performer2_1.h5', 'review_example_large', '2', 'Performer', '2', '1', 'review_example_large_predict_Performer_2feature_NameToInt.pickle', 'review_example_large_predict_Performer_2feature_IntToName.pickle', '0.6597360069166874'),
	('review_example_large2feature_Performer2_2.h5', 'review_example_large', '2', 'Performer', '2', '2', 'review_example_large_predict_Performer_2feature_NameToInt.pickle', 'review_example_large_predict_Performer_2feature_IntToName.pickle', '0.5929392593404107'),
	('review_example_large2feature_Performer3_1.h5', 'review_example_large', '2', 'Performer', '3', '1', 'review_example_large_predict_Performer_2feature_NameToInt.pickle', 'review_example_large_predict_Performer_2feature_IntToName.pickle', '0.6908831954880057'),
	('review_example_large2feature_Performer3_2.h5', 'review_example_large', '2', 'Performer', '3', '2', 'review_example_large_predict_Performer_2feature_NameToInt.pickle', 'review_example_large_predict_Performer_2feature_IntToName.pickle', '0.6025900188521495'),
	('review_example_large2feature_Performer3_3.h5', 'review_example_large', '2', 'Performer', '3', '3', 'review_example_large_predict_Performer_2feature_NameToInt.pickle', 'review_example_large_predict_Performer_2feature_IntToName.pickle', '0.5613073058964021'),
	('review_example_large2feature_Performer4_1.h5', 'review_example_large', '2', 'Performer', '4', '1', 'review_example_large_predict_Performer_2feature_NameToInt.pickle', 'review_example_large_predict_Performer_2feature_IntToName.pickle', '0.6562093099227786'),
	('review_example_large2feature_Activity_Performer2_1.h5', 'review_example_large', '2', 'Activity_Performer', '2', '1', 'review_example_large_predict_Activity_Performer_2feature_NameToInt.pickle', 'review_example_large_predict_Activity_Performer_2feature_IntToName.pickle', '0.6283598612401254'),
	('review_example_large2feature_Activity_Performer2_2.h5', 'review_example_large', '2', 'Activity_Performer', '2', '2', 'review_example_large_predict_Activity_Performer_2feature_NameToInt.pickle', 'review_example_large_predict_Activity_Performer_2feature_IntToName.pickle', '0.6243742509279904'),
	('review_example_large2feature_Activity_Performer3_1.h5', 'review_example_large', '2', 'Activity_Performer', '3', '1', 'review_example_large_predict_Activity_Performer_2feature_NameToInt.pickle', 'review_example_large_predict_Activity_Performer_2feature_IntToName.pickle', '0.6644505143520741'),
	('review_example_large2feature_Activity_Performer3_2.h5', 'review_example_large', '2', 'Activity_Performer', '3', '2', 'review_example_large_predict_Activity_Performer_2feature_NameToInt.pickle', 'review_example_large_predict_Activity_Performer_2feature_IntToName.pickle', '0.5988991960916235'),
	('review_example_large2feature_Activity_Performer3_3.h5', 'review_example_large', '2', 'Activity_Performer', '3', '3', 'review_example_large_predict_Activity_Performer_2feature_NameToInt.pickle', 'review_example_large_predict_Activity_Performer_2feature_IntToName.pickle', '0.5862692596731938'),
	('review_example_large2feature_Activity_Performer4_1.h5', 'review_example_large', '2', 'Activity_Performer', '4', '1', 'review_example_large_predict_Activity_Performer_2feature_NameToInt.pickle', 'review_example_large_predict_Activity_Performer_2feature_IntToName.pickle', '0.6226496568225407'),
	('review_example_large2feature_Activity_Performer4_2.h5', 'review_example_large', '2', 'Activity_Performer', '4', '2', 'review_example_large_predict_Activity_Performer_2feature_NameToInt.pickle', 'review_example_large_predict_Activity_Performer_2feature_IntToName.pickle', '0.6388617291959904'),
	('review_example_large2feature_Activity_Performer4_3.h5', 'review_example_large', '2', 'Activity_Performer', '4', '3', 'review_example_large_predict_Activity_Performer_2feature_NameToInt.pickle', 'review_example_large_predict_Activity_Performer_2feature_IntToName.pickle', '0.5852946109662734'),
	('review_example_large1feature_Performer2_1.h5', 'review_example_large', '1', 'Performer', '2', '1', 'review_example_large_predict_Performer_1feature_NameToInt.pickle', 'review_example_large_predict_Performer_1feature_IntToName.pickle', '0.5392998723348839'),
	('review_example_large1feature_Performer2_2.h5', 'review_example_large', '1', 'Performer', '2', '2', 'review_example_large_predict_Performer_1feature_NameToInt.pickle', 'review_example_large_predict_Performer_1feature_IntToName.pickle', '0.4383994630477719'),
	('review_example_large1feature_Performer3_1.h5', 'review_example_large', '1', 'Performer', '3', '1', 'review_example_large_predict_Performer_1feature_NameToInt.pickle', 'review_example_large_predict_Performer_1feature_IntToName.pickle', '0.5867593150802485'),
	('review_example_large1feature_Performer3_2.h5', 'review_example_large', '1', 'Performer', '3', '2', 'review_example_large_predict_Performer_1feature_NameToInt.pickle', 'review_example_large_predict_Performer_1feature_IntToName.pickle', '0.46769260307500576'),
	('review_example_large1feature_Performer3_3.h5', 'review_example_large', '1', 'Performer', '3', '3', 'review_example_large_predict_Performer_1feature_NameToInt.pickle', 'review_example_large_predict_Performer_1feature_IntToName.pickle', '0.44478496338717455'),
	('review_example_large1feature_Performer4_1.h5', 'review_example_large', '1', 'Performer', '4', '1', 'review_example_large_predict_Performer_1feature_NameToInt.pickle', 'review_example_large_predict_Performer_1feature_IntToName.pickle', '0.638578454298628'),
	('review_example_large1feature_Performer4_2.h5', 'review_example_large', '1', 'Performer', '4', '2', 'review_example_large_predict_Performer_1feature_NameToInt.pickle', 'review_example_large_predict_Performer_1feature_IntToName.pickle', '0.552437192793961'),
	('review_example_large1feature_Performer4_3.h5', 'review_example_large', '1', 'Performer', '4', '3', 'review_example_large_predict_Performer_1feature_NameToInt.pickle', 'review_example_large_predict_Performer_1feature_IntToName.pickle', '0.491740516122588'),
	('review_example_large1feature_Activity2_1.h5', 'review_example_large', '1', 'Activity', '2', '1', 'review_example_large_predict_Activity_1feature_NameToInt.pickle', 'review_example_large_predict_Activity_1feature_IntToName.pickle', '0.7069950355172078'),
	('review_example_large1feature_Activity2_2.h5', 'review_example_large', '1', 'Activity', '2', '2', 'review_example_large_predict_Activity_1feature_NameToInt.pickle', 'review_example_large_predict_Activity_1feature_IntToName.pickle', '0.6844830350243087'),
	('review_example_large1feature_Activity3_1.h5', 'review_example_large', '1', 'Activity', '3', '1', 'review_example_large_predict_Activity_1feature_NameToInt.pickle', 'review_example_large_predict_Activity_1feature_IntToName.pickle', '0.6878166672017679'),
	('review_example_large1feature_Activity3_2.h5', 'review_example_large', '1', 'Activity', '3', '2', 'review_example_large_predict_Activity_1feature_NameToInt.pickle', 'review_example_large_predict_Activity_1feature_IntToName.pickle', '0.71732682218518'),
	('review_example_large1feature_Activity4_1.h5', 'review_example_large', '1', 'Activity', '4', '1', 'review_example_large_predict_Activity_1feature_NameToInt.pickle', 'review_example_large_predict_Activity_1feature_IntToName.pickle', '0.7336133293126078'),
	('review_example_large1feature_Activity4_2.h5', 'review_example_large', '1', 'Activity', '4', '2', 'review_example_large_predict_Activity_1feature_NameToInt.pickle', 'review_example_large_predict_Activity_1feature_IntToName.pickle', '0.7281253419675283'),
	('review_example_large1feature_Activity4_3.h5', 'review_example_large', '1', 'Activity', '4', '3', 'review_example_large_predict_Activity_1feature_NameToInt.pickle', 'review_example_large_predict_Activity_1feature_IntToName.pickle', '0.6901511066197538'),
	('review_example_large1feature_Activity4_4.h5', 'review_example_large', '1', 'Activity', '4', '4', 'review_example_large_predict_Activity_1feature_NameToInt.pickle', 'review_example_large_predict_Activity_1feature_IntToName.pickle', '0.6710023764655983'),
	('review_example_large1feature_Activity5_1.h5', 'review_example_large', '1', 'Activity', '5', '1', 'review_example_large_predict_Activity_1feature_NameToInt.pickle', 'review_example_large_predict_Activity_1feature_IntToName.pickle', '0.6968688791511436'),
	('review_example_large1feature_Activity5_2.h5', 'review_example_large', '1', 'Activity', '5', '2', 'review_example_large_predict_Activity_1feature_NameToInt.pickle', 'review_example_large_predict_Activity_1feature_IntToName.pickle', '0.710056663118652'),
	('review_example_large1feature_Activity5_3.h5', 'review_example_large', '1', 'Activity', '5', '3', 'review_example_large_predict_Activity_1feature_NameToInt.pickle', 'review_example_large_predict_Activity_1feature_IntToName.pickle', '0.7088309117291495'),
	('review_example_large1feature_Activity5_4.h5', 'review_example_large', '1', 'Activity', '5', '4', 'review_example_large_predict_Activity_1feature_NameToInt.pickle', 'review_example_large_predict_Activity_1feature_IntToName.pickle', '0.693250688092806'),
	('review_example_large1feature_Activity6_3.h5', 'review_example_large', '1', 'Activity', '6', '3', 'review_example_large_predict_Activity_1feature_NameToInt.pickle', 'review_example_large_predict_Activity_1feature_IntToName.pickle', '0.6887103682293318'),
	('large bank transactions1feature_Activity2_1.h5', 'large bank transactions', '1', 'Activity', '2', '1', 'large bank transactions_predict_Activity_1feature_NameToInt.pickle', 'large bank transactions_predict_Activity_1feature_IntToName.pickle', '0.6451538292258375'),
	('large bank transactions1feature_Activity2_2.h5', 'large bank transactions', '1', 'Activity', '2', '2', 'large bank transactions_predict_Activity_1feature_NameToInt.pickle', 'large bank transactions_predict_Activity_1feature_IntToName.pickle', '0.582460060663295'),
	('large bank transactions1feature_Activity3_1.h5', 'large bank transactions', '1', 'Activity', '3', '1', 'large bank transactions_predict_Activity_1feature_NameToInt.pickle', 'large bank transactions_predict_Activity_1feature_IntToName.pickle', '0.7074175422525326'),
	('large bank transactions1feature_Activity3_2.h5', 'large bank transactions', '1', 'Activity', '3', '2', 'large bank transactions_predict_Activity_1feature_NameToInt.pickle', 'large bank transactions_predict_Activity_1feature_IntToName.pickle', '0.6240830876032386'),
	('large bank transactions1feature_Activity3_3.h5', 'large bank transactions', '1', 'Activity', '3', '3', 'large bank transactions_predict_Activity_1feature_NameToInt.pickle', 'large bank transactions_predict_Activity_1feature_IntToName.pickle', '0.5969493831416338'),
	('large bank transactions1feature_Activity4_1.h5', 'large bank transactions', '1', 'Activity', '4', '1', 'large bank transactions_predict_Activity_1feature_NameToInt.pickle', 'large bank transactions_predict_Activity_1feature_IntToName.pickle', '0.6990483878506746'),
	('large bank transactions1feature_Activity4_2.h5', 'large bank transactions', '1', 'Activity', '4', '2', 'large bank transactions_predict_Activity_1feature_NameToInt.pickle', 'large bank transactions_predict_Activity_1feature_IntToName.pickle', '0.6389290825981833'),
	('large bank transactions1feature_Activity5_1.h5', 'large bank transactions', '1', 'Activity', '5', '1', 'large bank transactions_predict_Activity_1feature_NameToInt.pickle', 'large bank transactions_predict_Activity_1feature_IntToName.pickle', '0.7085970648184169'),
	('large bank transactions1feature_Activity5_2.h5', 'large bank transactions', '1', 'Activity', '5', '2', 'large bank transactions_predict_Activity_1feature_NameToInt.pickle', 'large bank transactions_predict_Activity_1feature_IntToName.pickle', '0.635746075301787'),
	('large bank transactions1feature_Activity6_1.h5', 'large bank transactions', '1', 'Activity', '6', '1', 'large bank transactions_predict_Activity_1feature_NameToInt.pickle', 'large bank transactions_predict_Activity_1feature_IntToName.pickle', '0.6802669256785173');
/*!40000 ALTER TABLE `model` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
