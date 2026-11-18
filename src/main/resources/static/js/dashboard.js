
new Chart(document.getElementById("salesChart"), {
    type: "line",
    data: {
        labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
                 "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
        datasets: [{
            label: "Sales (₹)",
            data: [45000, 52000, 48000, 70000, 65000, 90000,
                   85000, 110000, 95000, 125000, 140000, 160000],
            borderColor: "#d6ae55",
            backgroundColor: "rgba(214,174,85,0.12)",
            fill: true,
            tension: 0.4
        }]
    },
    options: {
        responsive: true,
        plugins: {
            legend: {
                labels: { color: "#ddd" }
            }
        },
        scales: {
            x: {
                ticks: { color: "#aaa" },
                grid: { color: "#292929" }
            },
            y: {
                ticks: { color: "#aaa" },
                grid: { color: "#292929" }
            }
        }
    }
});

new Chart(document.getElementById("inventoryChart"), {
    type: "doughnut",
    data: {
        labels: ["Gold", "Silver", "Diamond"],
        datasets: [{
            data: [55, 25, 20],
            backgroundColor: ["#d6ae55", "#9da3aa", "#75a9d0"],
            borderWidth: 0
        }]
    },
    options: {
        responsive: true,
        plugins: {
            legend: { display: false }
        }
    }
});
