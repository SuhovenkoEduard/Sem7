const toDigits = (value, count) => {
  if (count >= 0) {
    if (value > 0) {
      return Math.floor(10 ** count * value) / (10 ** count).toFixed(count);
    } else {
      return Math.ceil(10 ** count * value) / (10 ** count).toFixed(count);
    }
  } else {
    throw Error;
  }
};

const menuState = {
  menu: "menu",
  Nichiporenko: "Nechiporenko_calculator",
  SMG: "SMG_calculator",
  LPNP: "LPNP_calculator",
  Fibrinogen: "Fibrinogen_calculator",
  title: "page_title",

  showNichiporenko() {
    document.getElementById(this.Nichiporenko).style.display = "flex";
    document.getElementById(this.title).innerText = "Нечипоренко";
    this.hideMenu();
  },

  showSMG() {
    document.getElementById(this.SMG).style.display = "flex";
    document.getElementById(this.title).innerText = "КА";
    this.hideMenu();
  },

  showLPNP() {
    document.getElementById(this.LPNP).style.display = "flex";
    document.getElementById(this.title).innerText = "ЛПНП";
    this.hideMenu();
  },

  showFibrinogen() {
    document.getElementById(this.Fibrinogen).style.display = "flex";
    document.getElementById(this.title).innerText = "Фибриноген";
    this.hideMenu();
  },

  hideMenu() {
    document.getElementById(this.menu).style.display = "none";
  },

  showMenu() {
    document.getElementById(this.Nichiporenko).style.display = "none";
    document.getElementById(this.SMG).style.display = "none";
    document.getElementById(this.LPNP).style.display = "none";
    document.getElementById(this.Fibrinogen).style.display = "none";
    document.getElementById(this.menu).style.display = "flex";
    document.getElementById(this.title).innerText = "Lab Calc";

    this.clearAllInputs();
  },

  clearAllInputs() {
    const inputs = document.getElementsByTagName("input");

    for (const input of inputs) {
      input.value = "";
    }
  },
};

// Нечипоренко
const calculate_N = () => {
  const x = +document.getElementById("input_x").value;

  document.getElementById("n16_result").value = (x * 16 * 0.031).toFixed(3);
  document.getElementById("n4_result").value = (x * 4 * 0.031).toFixed(3);
  document.getElementById("n2_result").value = (x * 2 * 0.031).toFixed(3);
  document.getElementById("n1_result").value = (x * 1 * 0.031).toFixed(3);

  document.getElementById("a16_result").value = ((x * 16) / 3).toFixed(3);
  document.getElementById("a4_result").value = ((x * 4) / 3).toFixed(3);
  document.getElementById("a2_result").value = ((x * 2) / 3).toFixed(3);
  document.getElementById("a1_result").value = ((x * 1) / 3).toFixed(3);
};
document.getElementById("input_x").addEventListener("input", calculate_N);
// ************

// КА
const calculate_KA = () => {
  const a = +document.getElementById("input_a").value;
  const b = +document.getElementById("input_b").value;

  if (a && b) {
    document.getElementById("KA_result").value = toDigits((a - b) / b, 2);
  } else {
    document.getElementById("KA_result").value = "";
  }
};
document.getElementById("input_a").addEventListener("input", calculate_KA);
document.getElementById("input_b").addEventListener("input", calculate_KA);
// ************

// ЛПНП
const calculate_LPNP = () => {
  const OXC = +document.getElementById("input_OXC").value;
  const LPVP = +document.getElementById("input_LPVP").value;
  const TG = +document.getElementById("input_TG").value;

  if (OXC && LPVP && TG) {
    document.getElementById("LPNP_result").value = (
      OXC -
      (LPVP + TG / 2.2)
    ).toFixed(1);
  } else {
    document.getElementById("LPNP_result").value = "";
  }
};
document.getElementById("input_OXC").addEventListener("input", calculate_LPNP);
document.getElementById("input_LPVP").addEventListener("input", calculate_LPNP);
document.getElementById("input_TG").addEventListener("input", calculate_LPNP);
// ************

// Фибриноген
const calculate_F = () => {
  const A2 = +document.getElementById("input_A2").value;
  const X2 = +document.getElementById("input_X2").value;

  if (A2 && X2) {
    const tempValue = toDigits(2.85 / X2, 2);
    document.getElementById("F_result").value = (A2 * tempValue).toFixed(1);
  } else {
    document.getElementById("F_result").value = "";
  }
};
document.getElementById("input_A2").addEventListener("input", calculate_F);
document.getElementById("input_X2").addEventListener("input", calculate_F);
// ************
