function openNotificationPanel() {
    $('#notifications-panel').addClass('active')
    let right = document.getElementById("notifications-panel").style.right;
    if (right !== '-420px') {
        document.getElementById("notifications-panel").style.right = "0";
    }
}

function closeNotificationPanel() {
    document.getElementById("notifications-panel").style.right = "-420px";
}

function markAsRead(notiId) {
  let noti = $('i#' + notiId);
  if (noti !== undefined) {
    noti.addClass('fa-circle-thin');
    noti.removeClass('fa-circle');
    noti.parents('.notifications-container-top').removeClass('p-text-bold');
  }
}

$(document).ready(function () {
    closeNotificationPanel();
    let notificationPanel = document.getElementById("notifications-panel");
    let bellIcon = document.getElementById('open-notifications-panel');
    document.addEventListener('click', event => {
        const isClickInside = notificationPanel.contains(event.target);
        const isClickOnBell = bellIcon.contains(event.target);

        if (!isClickInside && notificationPanel.style.right === '0px') {
            notificationPanel.style.right = "-420px";
        } else if (isClickOnBell) {
            notificationPanel.style.right = "0";
        }
    })
});
