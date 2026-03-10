# Bai 4.1 - Cyclomatic Complexity & Basis Path Testing

## Ham phan tich: `tinhPhiShip(double trongLuong, String vung, boolean laMember)`

---

## 1. CFG (Control Flow Graph)

```
ENTRY
  |
  N1: if (trongLuong <= 0)  [D1]
  |True               |False
  v                   v
N2: throw            N3: phi = 0
    exception         |
  [EXIT-E]            v
                    N4: if (vung == "noi_thanh")  [D2]
                    |True                  |False
                    v                      v
                   N5: phi=15000          N7: if (vung == "ngoai_thanh")  [D4]
                    |                     |True                  |False
                    v                     v                      v
                   N6: if(trongLuong>5)  N8: phi=25000          N10: phi=50000
                   [D3]                   |                       |
                   |True   |False         v                       v
                   v       |             N9: if(trongLuong>3)   N11: if(trongLuong>2)
                  phi+=..  |             [D5]                   [D6]
                   |       |             |True   |False         |True   |False
                   +------>|             v       |              v       |
                           |            phi+=..  |             phi+=..  |
                           v             |       |              |       |
                          merge1         +------>|              +------>|
                           |                     v                      v
                           |                   merge2                 merge3
                           |                     |                      |
                           +---------------------+----------------------+
                                                 |
                                                 v
                                           N12: if (laMember)  [D7]
                                           |True         |False
                                           v             |
                                          N13: phi*=0.9  |
                                           |             |
                                           +-------------+
                                                 |
                                                 v
                                           N14: return phi
                                            [EXIT-N]
                                                 |
                                                 v
                                             UNIFIED EXIT (N15)
```

**Danh sach node:**

- N1: if (trongLuong <= 0) [D1]
- N2: throw IllegalArgumentException
- N3: phi = 0
- N4: if (vung == "noi_thanh") [D2]
- N5: phi = 15000
- N6: if (trongLuong > 5) [D3]
- N7: if (vung == "ngoai_thanh") [D4]
- N8: phi = 25000
- N9: if (trongLuong > 3) [D5]
- N10: phi = 50000
- N11: if (trongLuong > 2) [D6]
- N12: if (laMember) [D7]
- N13: phi = phi \* 0.9
- N14: return phi
- N15: EXIT (unified)

---

## 2. Tinh Cyclomatic Complexity (CC)

### Phuong phap 1: Dem predicates

So dieu kien quyet dinh = 7 (D1, D2, D3, D4, D5, D6, D7)
**CC = 7 + 1 = 8**

### Phuong phap 2: Cong thuc CC = E - N + 2P

| Phan tu     | So luong |
| ----------- | -------- |
| N (nodes)   | 15       |
| E (edges)   | 21       |
| P (ket noi) | 1        |

Cac edge:
N1->N2, N1->N3, N2->N15, N3->N4,
N4->N5(D2=T), N4->N7(D2=F),
N5->N6, N6->phi+=(D3=T)->N12, N6->N12(D3=F),
N7->N8(D4=T), N7->N10(D4=F),
N8->N9, N9->phi+=(D5=T)->N12, N9->N12(D5=F),
N10->N11, N11->phi+=(D6=T)->N12, N11->N12(D6=F),
N12->N13(D7=T), N12->N14(D7=F),
N13->N14, N14->N15

**CC = E - N + 2P = 21 - 15 + 2x1 = 8** ✓

---

## 3. Tap Basis Path (8 duong co so)

| BP  | D1  | D2  | D3  | D4  | D5  | D6  | D7  | Ket qua                      |
| --- | --- | --- | --- | --- | --- | --- | --- | ---------------------------- |
| BP1 | T   | -   | -   | -   | -   | -   | -   | throw exception              |
| BP2 | F   | T   | T   | -   | -   | -   | T   | (15000+(8-5)*2000)*0.9=18900 |
| BP3 | F   | T   | F   | -   | -   | -   | F   | 15000                        |
| BP4 | F   | F   | -   | T   | T   | -   | F   | 25000+(5-3)\*3000=31000      |
| BP5 | F   | F   | -   | T   | F   | -   | T   | 25000\*0.9=22500             |
| BP6 | F   | F   | -   | F   | -   | T   | F   | 50000+(5-2)\*5000=65000      |
| BP7 | F   | F   | -   | F   | -   | F   | F   | 50000                        |
| BP8 | F   | F   | -   | F   | -   | F   | T   | 50000\*0.9=45000             |

---

## 4. Bang Test Case

| TC  | trongLuong | vung        | laMember | Ket qua mong doi | Basis Path |
| --- | ---------- | ----------- | -------- | ---------------- | ---------- |
| TC1 | -1         | noi_thanh   | false    | throw exception  | BP1        |
| TC2 | 8          | noi_thanh   | true     | 18900.0          | BP2        |
| TC3 | 3          | noi_thanh   | false    | 15000.0          | BP3        |
| TC4 | 5          | ngoai_thanh | false    | 31000.0          | BP4        |
| TC5 | 2          | ngoai_thanh | true     | 22500.0          | BP5        |
| TC6 | 5          | tinh_khac   | false    | 65000.0          | BP6        |
| TC7 | 1          | tinh_khac   | false    | 50000.0          | BP7        |
| TC8 | 1          | tinh_khac   | true     | 45000.0          | BP8        |

---

## 5. Ket luan

- **CC = 8** => Can toi thieu **8 test case** de phu het Basis Paths.
- 8 test case tren la tuyen tinh doc lap, phu het moi nhanh True/False cua 7 dieu kien.
- Chay thu: `mvn test` tai thu muc `bai4.1/`

---

## 6. Huong dan chay

```
cd C:\HCMUNRE_KiemThu\LAB8\bai4\bai4.1
mvn test
```

Bao cao HTML: `target/surefire-reports/index.html`
