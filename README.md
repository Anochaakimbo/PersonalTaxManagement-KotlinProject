```
app.put('/updateTaxDeduction/:id', function (req, res) {
    let taxdeduction_id = req.params.id;
    let { taxdeductiontype_balance, taxdeductiontype_id, user_id, year } = req.body;

    if (!taxdeduction_id || !taxdeductiontype_balance || !taxdeductiontype_id || !user_id || !year) {
        return res.status(400).send({ error: true, message: "Please provide full tax deduction data and the id." });
    }

    let updateQuery = `
        UPDATE taxdeduction
        SET taxdeductiontype_balance = ?, taxdeductiontype_id = ?, user_id = ?, year = ?
        WHERE id = ?
    `;
    
    dbConn.query(updateQuery, [taxdeductiontype_balance, taxdeductiontype_id, user_id, year, taxdeduction_id], (error, results) => {
        if (error) {
            console.log("Error updating tax deduction: ", error);
            return res.status(500).send({ error: true, message: "Error updating tax deduction" });
        }
        return res.send({ success: 1, message: "Tax deduction updated successfully." });
    });
});
```

```
app.put('/updateIncome/:id', function (req, res) {
    let income_id = req.params.id;
    let { incomebalance, incometype_id, user_id, year } = req.body;

    if (!income_id || !incomebalance || !incometype_id || !user_id || !year) {
        return res.status(400).send({ error: true, message: "Please provide full income data and the id." });
    }

    let updateQuery = `
        UPDATE income 
        SET incomebalance = ?, incometype_id = ?, user_id = ?, year = ?
        WHERE id = ?
    `;
    
    dbConn.query(updateQuery, [incomebalance, incometype_id, user_id, year, income_id], (error, results) => {
        if (error) {
            console.log("Error updating income: ", error);
            return res.status(500).send({ error: true, message: "Error updating income" });
        }
        return res.send({ success: 1, message: "Income updated successfully." });
    });
});
```

```
app.post("/insertTaxDeduction", async function (req, res) {
    let { taxdeductiontype_balance, taxdeductiontype_id, email, year } = req.body;

    if (!taxdeductiontype_balance || !year || !taxdeductiontype_id || !email) {
        return res.status(400).send({ error: true, message: "กรุณากรอกข้อมูลให้ครบถ้วน" });
    }

    if (isNaN(taxdeductiontype_balance) || isNaN(taxdeductiontype_id)) {
        return res.status(400).send({ error: true, message: "ค่าที่ส่งมาต้องเป็นตัวเลข" });
    }

    // ✅ 1. ค้นหา user_id จาก email
    let findUserQuery = "SELECT id FROM user WHERE email = ?";
    
    dbConn.query(findUserQuery, [email], (error, userResults) => {
        if (error) {
            console.log("❌ Error ในการค้นหา user_id:", error);
            return res.status(500).send({ error: true, message: "เกิดข้อผิดพลาดในการค้นหาผู้ใช้" });
        }

        if (userResults.length === 0) {
            return res.status(400).send({ error: true, message: "ไม่พบผู้ใช้ที่มีอีเมลนี้" });
        }

        let user_id = userResults[0].id;

        // ✅ 2. ค้นหา taxdeductiontype_name จาก taxdeductiontype_id
        let findTaxDeductionTypeQuery = "SELECT taxdeductiontype_name FROM taxdeduction_type WHERE id = ?";
        
        dbConn.query(findTaxDeductionTypeQuery, [taxdeductiontype_id], (error, taxResults) => {
            if (error) {
                console.log("❌ Error ในการค้นหา taxdeductiontype_name:", error);
                return res.status(500).send({ error: true, message: "เกิดข้อผิดพลาดในการค้นหาประเภทค่าลดหย่อน" });
            }

            if (taxResults.length === 0) {
                return res.status(400).send({ error: true, message: "ไม่พบค่าลดหย่อนที่เลือก" });
            }

            let taxdeductiontype_name = taxResults[0].taxdeductiontype_name;

            // ✅ 3. บันทึกข้อมูลค่าลดหย่อนลงในตาราง taxdeduction
            let insertQuery = `
                INSERT INTO taxdeduction (taxdeductiontype_balance, taxdeductiontype_id, user_id, year)
                VALUES (?, ?, ?, ?)
            `;

            let values = [taxdeductiontype_balance, taxdeductiontype_id, user_id, year];

            dbConn.query(insertQuery, values, (error, results) => {
                if (error) {
                    console.log("❌ Error ในการบันทึก:", error);
                    return res.status(500).send({ error: true, message: "เกิดข้อผิดพลาดในการบันทึกข้อมูล" });
                }

                return res.send({
                    success: 1,
                    id: results.insertId,
                    taxdeductiontype_name: taxdeductiontype_name,
                    message: "เพิ่มข้อมูลค่าลดหย่อนสำเร็จ"
                });
            });
        });
    });
});
```
