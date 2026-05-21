// Light/dark theme. Preference is remembered in localStorage and applied
// before paint to avoid a flash. Works fully offline.
(function () {
  var KEY = "pathfinder-theme";
  var saved = null;
  try { saved = localStorage.getItem(KEY); } catch (e) {}
  var prefersDark = window.matchMedia &&
                    window.matchMedia("(prefers-color-scheme: dark)").matches;
  var theme = saved || (prefersDark ? "dark" : "light");
  document.documentElement.setAttribute("data-theme", theme);

  window.toggleTheme = function () {
    var current = document.documentElement.getAttribute("data-theme");
    var next = current === "dark" ? "light" : "dark";
    document.documentElement.setAttribute("data-theme", next);
    try { localStorage.setItem(KEY, next); } catch (e) {}
    var label = document.getElementById("theme-label");
    if (label) label.textContent = next === "dark" ? "Light" : "Dark";
  };
})();
