-- =====================================
-- schema.sql  (resources/schema.sql)
-- =====================================
-- テーブルクリア
DROP TABLE IF EXISTS shain_m;
DROP TABLE IF EXISTS kintai_data;
DROP TABLE IF EXISTS shift_data;

-- 社員マスタ
CREATE TABLE shain_m (
    shain_id       	BIGINT PRIMARY KEY AUTO_INCREMENT,
    shain_name     VARCHAR(100) NOT NULL
);

-- 勤怠データ
CREATE TABLE kintai_data (
    kintai_id       	BIGINT PRIMARY KEY AUTO_INCREMENT,
    shain_id			INT NOT NULL,
	arrival_date_time DATETIME  NOT NULL,
	finish_date_time	DATETIME  NOT NULL
);

-- シフトデータ
CREATE TABLE shift_data (
    shift_id       	BIGINT PRIMARY KEY,
    shift_ym		INT NOT NULL,
    shain_id			INT NOT NULL,
	shift_date  DATE NOT NULL,
	shift_um BOOLEAN NOT NULL,
	ampm	VARCHAR(1)
);

-- 売上データ
CREATE TABLE uriage_data (
    uriage_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    uriage_ymd DATE NOT NULL,
    uriage_shiten_id INT NOT NULL,
    uriage_gaku INT NOT NULL
);

-- 支店マスタ
CREATE TABLE shiten_m (
	shiten_id INT PRIMARY KEY,
	shiten_name VARCHAR(100)
);

-- 材料マスタ
CREATE TABLE zairyou_m (
	zairyou_id		BIGINT PRIMARY KEY AUTO_INCREMENT,
	zairyou_name VARCHAR(100) NOT NULL
);

-- 発注データ
CREATE TABLE order_data (
	order_id BIGINT PRIMARY KEY AUTO_INCREMENT,
	order_date DATE NOT NULL,
	order_shiten_id INT NOT NULL,
	zairyou_id INT NOT NULL,
	order_amount INT NOT NULL
);
