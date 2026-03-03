"use strict";

/* =====================================================
   ShopVN – Registration Form Validation Logic
   ===================================================== */

// ---------- DỮ LIỆU GIẢ LẬP (mock data) ----------
const EXISTING_USERNAMES = [
  "admin",
  "shopvn",
  "user01",
  "testuser",
  "nguyen123",
];
const EXISTING_EMAILS = [
  "admin@shopvn.vn",
  "test@gmail.com",
  "user@example.com",
];
const VALID_REFERRAL_CODES = ["SHOP2024", "VN123456", "GIFT0001", "PROMO999"];

// ---------- DOM REFERENCES ----------
const form = document.getElementById("registerForm");
const successBanner = document.getElementById("successBanner");
const viewTermsLink = document.getElementById("viewTermsLink");
const termsModal = document.getElementById("termsModal");
const closeModal = document.getElementById("closeModal");
const cancelTermsBtn = document.getElementById("cancelTermsBtn");
const acceptTermsBtn = document.getElementById("acceptTermsBtn");
const agreeCheckbox = document.getElementById("agreeTerms");

/* =====================================================
   HELPER – set field state (valid / invalid / neutral)
   ===================================================== */
function setFieldState(fieldId, errorId, message) {
  const field = document.getElementById(fieldId);
  const errorEl = document.getElementById(errorId);

  if (!field || !errorEl) return;

  if (message) {
    // Invalid state
    field.classList.remove("valid");
    field.classList.add("invalid");
    errorEl.textContent = message;
  } else {
    // Valid state
    field.classList.remove("invalid");
    field.classList.add("valid");
    errorEl.textContent = "";
  }
}

/* Reset all field states */
function clearFieldState(fieldId, errorId) {
  const field = document.getElementById(fieldId);
  const errorEl = document.getElementById(errorId);
  if (field) {
    field.classList.remove("valid", "invalid");
  }
  if (errorEl) {
    errorEl.textContent = "";
  }
}

/* =====================================================
   VALIDATION FUNCTIONS – trả về null nếu hợp lệ,
   trả về chuỗi lỗi nếu không hợp lệ
   ===================================================== */

function validateFullName(value) {
  if (!value || value.trim() === "") return "Họ và tên không được để trống.";
  const trimmed = value.trim();
  if (trimmed.length < 2) return "Họ và tên phải có ít nhất 2 ký tự.";
  if (trimmed.length > 50) return "Họ và tên không được vượt quá 50 ký tự.";
  if (!/^[\p{L}\s]+$/u.test(trimmed))
    return "Họ và tên chỉ được chứa chữ cái và dấu cách.";
  return null;
}

function validateUsername(value) {
  if (!value || value.trim() === "")
    return "Tên đăng nhập không được để trống.";
  const v = value.trim();
  if (v.length < 5) return "Tên đăng nhập phải có ít nhất 5 ký tự.";
  if (v.length > 20) return "Tên đăng nhập không được vượt quá 20 ký tự.";
  if (!/^[a-z]/.test(v))
    return "Tên đăng nhập phải bắt đầu bằng chữ cái thường.";
  if (!/^[a-z][a-z0-9_]*$/.test(v))
    return "Tên đăng nhập chỉ được chứa chữ thường, số và dấu gạch dưới (_).";
  if (EXISTING_USERNAMES.includes(v))
    return "Tên đăng nhập đã tồn tại. Vui lòng chọn tên khác.";
  return null;
}

function validateEmail(value) {
  if (!value || value.trim() === "") return "Email không được để trống.";
  const v = value.trim().toLowerCase();
  // RFC-style simple email regex
  if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(v))
    return "Email không đúng định dạng.";
  if (EXISTING_EMAILS.includes(v))
    return "Email đã được đăng ký. Vui lòng dùng email khác.";
  return null;
}

function validatePhone(value) {
  if (!value || value.trim() === "")
    return "Số điện thoại không được để trống.";
  const v = value.trim();
  if (!/^0\d{9}$/.test(v))
    return "Số điện thoại phải bắt đầu bằng 0 và gồm đúng 10 chữ số.";
  return null;
}

function validatePassword(value) {
  if (!value) return "Mật khẩu không được để trống.";
  if (value.length < 8) return "Mật khẩu phải có ít nhất 8 ký tự.";
  if (value.length > 32) return "Mật khẩu không được vượt quá 32 ký tự.";
  if (!/[A-Z]/.test(value)) return "Mật khẩu phải chứa ít nhất 1 chữ hoa.";
  if (!/[a-z]/.test(value)) return "Mật khẩu phải chứa ít nhất 1 chữ thường.";
  if (!/\d/.test(value)) return "Mật khẩu phải chứa ít nhất 1 chữ số.";
  if (!/[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?`~]/.test(value))
    return "Mật khẩu phải chứa ít nhất 1 ký tự đặc biệt (vd: @, #, !).";
  return null;
}

function validateConfirmPassword(pw, confirm) {
  if (!confirm) return "Vui lòng nhập lại mật khẩu.";
  if (pw !== confirm) return "Xác nhận mật khẩu không khớp.";
  return null;
}

function validateDob(value) {
  if (!value) return null; // optional
  const dob = new Date(value);
  if (isNaN(dob.getTime())) return "Ngày sinh không hợp lệ.";

  const today = new Date();
  // Tính tuổi chính xác
  let age = today.getFullYear() - dob.getFullYear();
  const m = today.getMonth() - dob.getMonth();
  if (m < 0 || (m === 0 && today.getDate() < dob.getDate())) age--;

  if (age < 16) return "Bạn phải đủ 16 tuổi để đăng ký.";
  if (age >= 100) return "Ngày sinh không hợp lệ (tuổi phải dưới 100).";
  return null;
}

function validateReferralCode(value) {
  if (!value || value.trim() === "") return null; // optional
  const v = value.trim().toUpperCase();
  if (!/^[A-Z0-9]{8}$/.test(v))
    return "Mã giới thiệu phải gồm đúng 8 ký tự chữ hoa và số.";
  if (!VALID_REFERRAL_CODES.includes(v))
    return "Mã giới thiệu không tồn tại hoặc đã hết hiệu lực.";
  return null;
}

function validateAgreeTerms(checked) {
  if (!checked) return "Bạn phải đồng ý với điều khoản sử dụng để tiếp tục.";
  return null;
}

/* =====================================================
   PASSWORD STRENGTH INDICATOR
   ===================================================== */
function getPasswordStrength(pw) {
  if (!pw) return { label: "", cls: "" };
  let score = 0;
  if (pw.length >= 8) score++;
  if (pw.length >= 12) score++;
  if (/[A-Z]/.test(pw)) score++;
  if (/[a-z]/.test(pw)) score++;
  if (/\d/.test(pw)) score++;
  if (/[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?`~]/.test(pw)) score++;

  if (score <= 2) return { label: "● Yếu", cls: "strength-weak" };
  if (score <= 3) return { label: "●● Trung bình", cls: "strength-fair" };
  if (score <= 4) return { label: "●●● Khá mạnh", cls: "strength-good" };
  return { label: "●●●● Mạnh", cls: "strength-strong" };
}

/* =====================================================
   FULL FORM VALIDATION – validate all, return isValid
   ===================================================== */
function validateAll() {
  const fields = {
    fullName: validateFullName(document.getElementById("fullName").value),
    username: validateUsername(document.getElementById("username").value),
    email: validateEmail(document.getElementById("email").value),
    phone: validatePhone(document.getElementById("phone").value),
    password: validatePassword(document.getElementById("password").value),
    confirmPassword: validateConfirmPassword(
      document.getElementById("password").value,
      document.getElementById("confirmPassword").value,
    ),
    dob: validateDob(document.getElementById("dob").value),
    referralCode: validateReferralCode(
      document.getElementById("referralCode").value,
    ),
    agreeTerms: validateAgreeTerms(
      document.getElementById("agreeTerms").checked,
    ),
  };

  // map field names to their error element IDs
  const errorIds = {
    fullName: "fullNameError",
    username: "usernameError",
    email: "emailError",
    phone: "phoneError",
    password: "passwordError",
    confirmPassword: "confirmPasswordError",
    dob: "dobError",
    referralCode: "referralCodeError",
    agreeTerms: "agreeTermsError",
  };

  // checkbox doesn't have a standard input element with valid/invalid class
  const noClassFields = ["agreeTerms"];

  let isValid = true;
  let firstInvalidId = null;

  for (const [key, errMsg] of Object.entries(fields)) {
    setFieldState(key, errorIds[key], errMsg);

    if (noClassFields.includes(key) && errMsg) {
      // For checkbox just show error text, don't add class
      document.getElementById(key).classList.remove("valid");
      document.getElementById(key).classList.remove("invalid");
    }

    if (errMsg) {
      isValid = false;
      if (!firstInvalidId) firstInvalidId = key;
    }
  }

  // Focus first invalid field
  if (firstInvalidId) {
    const el = document.getElementById(firstInvalidId);
    if (el && el.focus) el.focus();
  }

  return isValid;
}

/* =====================================================
   REAL-TIME (blur) VALIDATION – validate single field
   ===================================================== */
function attachBlurValidation() {
  const blurMap = [
    {
      id: "fullName",
      validate: () =>
        validateFullName(document.getElementById("fullName").value),
      errId: "fullNameError",
    },
    {
      id: "username",
      validate: () =>
        validateUsername(document.getElementById("username").value),
      errId: "usernameError",
    },
    {
      id: "email",
      validate: () => validateEmail(document.getElementById("email").value),
      errId: "emailError",
    },
    {
      id: "phone",
      validate: () => validatePhone(document.getElementById("phone").value),
      errId: "phoneError",
    },
    {
      id: "password",
      validate: () =>
        validatePassword(document.getElementById("password").value),
      errId: "passwordError",
    },
    {
      id: "confirmPassword",
      validate: () =>
        validateConfirmPassword(
          document.getElementById("password").value,
          document.getElementById("confirmPassword").value,
        ),
      errId: "confirmPasswordError",
    },
    {
      id: "dob",
      validate: () => validateDob(document.getElementById("dob").value),
      errId: "dobError",
    },
    {
      id: "referralCode",
      validate: () =>
        validateReferralCode(document.getElementById("referralCode").value),
      errId: "referralCodeError",
    },
  ];

  blurMap.forEach(({ id, validate, errId }) => {
    const el = document.getElementById(id);
    if (!el) return;
    el.addEventListener("blur", () => {
      const err = validate();
      setFieldState(id, errId, err);
    });
    el.addEventListener("input", () => {
      // Reset state while typing so user doesn't see stale red
      clearFieldState(id, errId);
    });
  });

  // Checkbox feedback on change
  agreeCheckbox.addEventListener("change", () => {
    const err = validateAgreeTerms(agreeCheckbox.checked);
    document.getElementById("agreeTermsError").textContent = err || "";
  });
}

/* =====================================================
   PASSWORD STRENGTH – live indicator
   ===================================================== */
document.getElementById("password").addEventListener("input", function () {
  const strengthEl = document.getElementById("passwordStrength");
  const s = getPasswordStrength(this.value);
  strengthEl.textContent = s.label;
  strengthEl.className = "password-strength " + s.cls;
});

/* =====================================================
   TOGGLE PASSWORD VISIBILITY
   ===================================================== */
document.querySelectorAll(".toggle-pw").forEach((btn) => {
  btn.addEventListener("click", () => {
    const targetId = btn.getAttribute("data-target");
    const input = document.getElementById(targetId);
    if (!input) return;
    input.type = input.type === "password" ? "text" : "password";
    btn.textContent = input.type === "password" ? "👁" : "🙈";
  });
});

/* =====================================================
   FORM SUBMIT
   ===================================================== */
form.addEventListener("submit", function (e) {
  e.preventDefault(); // Không reload trang

  if (!validateAll()) return;

  // Thu thập dữ liệu đã qua validation
  const formData = {
    fullName: document.getElementById("fullName").value.trim(),
    username: document.getElementById("username").value.trim(),
    email: document.getElementById("email").value.trim().toLowerCase(),
    phone: document.getElementById("phone").value.trim(),
    dob: document.getElementById("dob").value || null,
    gender: document.getElementById("gender").value || null,
    referralCode:
      document.getElementById("referralCode").value.trim().toUpperCase() ||
      null,
    registeredAt: new Date().toISOString(),
  };

  // Log dữ liệu ra console (theo yêu cầu)
  console.log("=== ĐĂNG KÝ THÀNH CÔNG ===");
  console.table(formData);

  // Ẩn form, hiện thông báo thành công
  form.style.display = "none";
  successBanner.style.display = "block";
});

/* =====================================================
   RESET FORM (dùng cho nút "Đăng ký tài khoản khác")
   ===================================================== */
function resetForm() {
  form.reset();
  document.getElementById("passwordStrength").textContent = "";
  document.getElementById("passwordStrength").className = "password-strength";

  // Clear all field states
  const allInputs = form.querySelectorAll("input, select");
  allInputs.forEach((el) => el.classList.remove("valid", "invalid"));

  const allErrors = form.querySelectorAll(".error-msg");
  allErrors.forEach((el) => {
    el.textContent = "";
  });

  successBanner.style.display = "none";
  form.style.display = "block";
  document.getElementById("fullName").focus();
}

/* =====================================================
   TERMS MODAL
   ===================================================== */

// Mở modal khi click "Xem Điều khoản"
viewTermsLink.addEventListener("click", function (e) {
  e.preventDefault();
  openModal();
});

// Đóng modal
closeModal.addEventListener("click", closeModalFn);
cancelTermsBtn.addEventListener("click", closeModalFn);

// Đồng ý điều khoản → tự tick checkbox + đóng modal
acceptTermsBtn.addEventListener("click", () => {
  agreeCheckbox.checked = true;
  document.getElementById("agreeTermsError").textContent = "";
  closeModalFn();
});

// Click ngoài modal để đóng
termsModal.addEventListener("click", function (e) {
  if (e.target === termsModal) closeModalFn();
});

// Phím Escape để đóng modal
document.addEventListener("keydown", function (e) {
  if (e.key === "Escape" && termsModal.style.display !== "none") closeModalFn();
});

function openModal() {
  termsModal.style.display = "flex";
  document.body.style.overflow = "hidden";
  closeModal.focus();
}

function closeModalFn() {
  termsModal.style.display = "none";
  document.body.style.overflow = "";
  viewTermsLink.focus();
}

/* =====================================================
   INIT
   ===================================================== */
attachBlurValidation();
