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

-- Dumping structure for table processmining.functionlist
CREATE TABLE IF NOT EXISTS `functionlist` (
  `id` text COLLATE utf8_bin DEFAULT NULL,
  `name` text COLLATE utf8_bin DEFAULT NULL,
  `parameter` text COLLATE utf8_bin DEFAULT NULL,
  `describer` text COLLATE utf8_bin DEFAULT NULL,
  `outputType` text COLLATE utf8_bin DEFAULT NULL,
  `output` text COLLATE utf8_bin DEFAULT NULL,
  `run` text COLLATE utf8_bin DEFAULT NULL,
  `position` text COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Dumping data for table processmining.functionlist: ~5 rows (approximately)
DELETE FROM `functionlist`;
/*!40000 ALTER TABLE `functionlist` DISABLE KEYS */;
INSERT INTO `functionlist` (`id`, `name`, `parameter`, `describer`, `outputType`, `output`, `run`, `position`) VALUES
	('SummaryAllActivity', 'See activites information', 'activity: abc\nDHIL: ahg', 'Calculate summary of activities', 'ImageFilePath', '/Users/phamdinhlam/Dropbox/Programing/PredictWSESN/Python/OutputImage/SummaryAllActivity.png', 'displayed', 'DetailPanel3\n'),
	('SummaryAllPerformer', 'Let see performer information', 'def', 'Calculate summary of performers', 'ImageFilePath', '/Users/phamdinhlam/Dropbox/Programing/PredictWSESN/Python/OutputImage/SummaryAllPerformer.png', 'displayed', 'DetailPanel3'),
	('PredictNext', 'Predict next', 'LogFile!!BPI_Challenge_2019\nStepIn!!5\nStepOut!!1\nPredictType!!Activity\nFeature!!1\nInput!!Record Invoice Receipt\nInput!!Record Goods Receipt\nInput!!Record Service Entry Sheet\nInput!!Record Invoice Receipt\nInput!!Clear Invoice', 'Logfile: abc.xes\nStepIn: 3\nStepOut: 1\nPredictType: Activities/Performers\nFeature: 1_activity/2_activity_and_performer\nact1\nact2\nact3', 'String', 'END', 'displayed', NULL),
	('PredictESN', 'Predict enterprise social network', 'LogFile!!review_example_large\nPredictType!!Activity_Performer\nStepIn!!5\nStepOut!!1\nFeature!!2\nMaxLoopStep!!30\nFileName!!review_example_large_inputForPredictESN.txt', 'This function tries to read traces from input trace list and then pass parameter through Python to get predict full trace result.\n\nParameters:\nLogFile:review_example_large\nPredictType:Activity_Performer\nFeature:2\nInput:trace1\ninput:trace2\ninput:trace...\ninput:tracen', NULL, 'Trained model does not exist!', 'notyet', NULL),
	('GetTracesStatistics', 'Get Statistic Information from traces', 'SubLogFile!!_subTraces.txt\nType!!Activity', 'Get Statistic Information from traces\nOutput:\nfilepath1_barchart.png!!filepath2_barchart.png', NULL, 'Chart1.png!!Chart2.png', 'displayed', NULL);
/*!40000 ALTER TABLE `functionlist` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
