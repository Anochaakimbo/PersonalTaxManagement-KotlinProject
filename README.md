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
