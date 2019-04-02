ALTER TABLE receipt_manage ALTER COLUMN status SET DEFAULT 1;
ALTER TABLE receipt_manage ALTER COLUMN is_measure_receipt SET DEFAULT 0;
ALTER TABLE receipt_manage ALTER COLUMN is_box_count SET DEFAULT 0;
ALTER TABLE receipt_manage ALTER COLUMN is_quantity_count SET DEFAULT 0;
ALTER TABLE receipt_manage ALTER COLUMN is_weight_receipt SET DEFAULT 0;
ALTER TABLE receipt_manage ALTER COLUMN is_gross_weight SET DEFAULT 0;
ALTER TABLE receipt_manage ALTER COLUMN is_net_weight SET DEFAULT 0;
ALTER TABLE receipt_manage ALTER COLUMN is_volume_receipt SET DEFAULT 0;
ALTER TABLE receipt_manage ALTER COLUMN is_batch_receipt SET DEFAULT 0;
ALTER TABLE receipt_manage MODIFY status tinyint(1) DEFAULT '1' COMMENT '状态 1-待收货 2-已收货 3-收货中 4-待入储';
ALTER TABLE inventory_manage CONVERT TO CHARACTER SET utf8 COLLATE utf8_bin;
ALTER TABLE outgoing_scan_manage ALTER COLUMN is_box_num SET DEFAULT 2;
ALTER TABLE outgoing_scan_manage ALTER COLUMN is_box_son_num SET DEFAULT 2;
ALTER TABLE outgoing_scan_manage ALTER COLUMN is_serial_num SET DEFAULT 2;
ALTER TABLE outgoing_scan_manage ALTER COLUMN is_board_num SET DEFAULT 1;
ALTER TABLE outgoing_scan_manage ALTER COLUMN is_scan SET DEFAULT 1;
ALTER TABLE outgoing_scan_manage ALTER COLUMN status SET DEFAULT 1;

ALTER TABLE warehouse_storage_grade ADD region tinyint(1) NULL COMMENT '对应区域 1-良品区 2-不良品区 3-待检区 4-待收区 5-发货区';

DROP INDEX number ON warehouse_storage;

ALTER TABLE inventory_manage ADD available_stock decimal(10,1) NULL COMMENT '可用库存';
ALTER TABLE inventory_manage ADD lock_stock decimal(10,1) NULL COMMENT '锁定库存';