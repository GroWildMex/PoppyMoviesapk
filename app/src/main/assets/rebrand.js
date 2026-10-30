(function () {
  var NAME = 'Poppy Movies';
  var timer = null;

  function rebrandText() {
    var walker = document.createTreeWalker(document.body, NodeFilter.SHOW_TEXT, null, false);
    var n;
    while ((n = walker.nextNode())) {
      if (n.nodeValue && n.nodeValue.indexOf('Heartive') !== -1) {
        n.nodeValue = n.nodeValue.replace(/Heartive/gi, NAME);
      }
    }
    if (document.title && document.title.indexOf('Heartive') !== -1) {
      document.title = document.title.replace(/Heartive/gi, NAME);
    }
  }

  function removeNewsTab() {
    document.querySelectorAll('a').forEach(function (a) {
      var t = (a.textContent || '').trim().toLowerCase();
      if (t === 'news') (a.closest('li') || a).remove();
    });
  }

  function addTrailers() {
    document.querySelectorAll('a[href*="/movie/"], a[href*="/series/"], a[href*="/tv/"]').forEach(function (a) {
      if (a.querySelector('.poppy-trailer')) return;
      var title = (a.getAttribute('title') || a.textContent || '').trim();
      if (!title || title.length < 2 || title.length > 80) return;

      var btn = document.createElement('button');
      btn.className = 'poppy-trailer';
      btn.textContent = '▶ Trailer';
      btn.setAttribute('style',
        'margin:6px 4px 0;padding:6px 10px;border:0;border-radius:8px;' +
        'background:#f6a21c;color:#1a1206;font-weight:700;font-size:12px;cursor:pointer');
      btn.addEventListener('click', function (e) {
        e.preventDefault();
        e.stopPropagation();
        var q = encodeURIComponent(title + ' official trailer');
        window.open('https://www.youtube.com/results?search_query=' + q, '_blank');
      });
      a.appendChild(btn);
    });
  }

  function runAll() {
    try { rebrandText(); } catch (e) {}
    try { removeNewsTab(); } catch (e) {}
    try { addTrailers(); } catch (e) {}
  }

  function schedule() {
    if (timer) clearTimeout(timer);
    timer = setTimeout(function () {
      timer = null;
      runAll();
    }, 300);
  }

  function start() {
    runAll();
    new MutationObserver(schedule).observe(document.documentElement, {
      childList: true,
      subtree: true
    });
  }

  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', start);
  } else {
    start();
  }
})();
