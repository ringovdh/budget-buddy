INSERT INTO category(id, label, icon, fixedCost, inDetails, inMonitor, limitAmount, revenue, saving)
    VALUES(1, 'Test category', '', false, true, false, 100.00, false, false);
INSERT INTO transaction(tx_id, number, amount, sign, date, project_id, comment, category)
    VALUES(1, '001', 10, '-', '2024-01-09', null, '', 1);