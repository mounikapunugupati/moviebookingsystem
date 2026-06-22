// ===== script.js =====

// ---------- MOCK DATA ----------
let movies = [];
let theaters = [];
let bookings = [];
let currentUser = null;
let selectedSeats = [];
let currentMovieId = null;
let currentShowTime = null;
let currentShowId = null;
let seatPrice = 220;
let selectedUpi = 'user@paytm';
let paymentMethod = 'upi';
let userProfile = {
};

// ---------- DOM refs ----------
function $(id) { return document.getElementById(id); }

// ---------- PAGE NAV ----------
function showPage(pageId) {
    document.querySelectorAll('.page').forEach(p => p.classList.remove('active'));
    const el = $(pageId);
    if (el) el.classList.add('active');
    document.querySelectorAll('.nav-link').forEach(a => a.classList.remove('active-link'));
    const map = { homePage: 'navHome',theatersPage: 'navTheaters', moviesPage: 'navMovies', bookingsPage: 'navBookings', loginPage: 'navLogin', profilePage: 'navProfile' };
    if (map[pageId]) $(map[pageId])?.classList.add('active-link');
    if (pageId === 'theatersPage')  loadTheaters();
    if (pageId === 'moviesPage') renderMovies();
    if (pageId === 'homePage') renderFeatured();
   if (pageId === 'bookingsPage') {
    loadUserBookings().then(() => {
        renderBookings();
    });
}
    if (pageId === 'profilePage') renderProfile();
  if (pageId === 'profilePage') {
    loadProfile();
    }
    updateNavVisibility();
}

function updateNavVisibility() {
    const loggedIn = !!currentUser;
    $('navLogin').style.display = loggedIn ? 'none' : 'inline';
    $('navLogout').style.display = loggedIn ? 'inline' : 'none';
    $('navProfile').style.display = loggedIn ? 'inline' : 'none';
}

// ---------- AUTH ----------
function switchAuth(tab) {
    document.querySelectorAll('.auth-tab').forEach(t => t.classList.remove('active'));
    document.querySelectorAll('.auth-form').forEach(f => f.classList.remove('active'));
    if (tab === 'login') {
        document.querySelector('.auth-tab:first-child').classList.add('active');
        $('loginForm').classList.add('active');
    } else {
        document.querySelector('.auth-tab:last-child').classList.add('active');
        $('registerForm').classList.add('active');
    }
}

function handleLogin(event) {
    event.preventDefault();

    const loginData = {
        email: document.getElementById("loginEmail").value,
        password: document.getElementById("loginPassword").value
    };

    fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(loginData)
    })
    .then(response => response.text())
    .then(async (token) => {
        localStorage.setItem("token", token);

        currentUser = {
            email: loginData.email
        };

        // Load user profile and bookings after login
        await loadProfile();
        await loadUserBookings();

        alert("Login Successful");

        showPage("homePage");
        updateNavVisibility();
    })
    .catch(error => {
        console.error(error);
        alert("Login failed. Please check your credentials.");
    });
}

function handleRegister(event) {
    event.preventDefault();

    const user = {
        name: document.getElementById("regName").value,
        email: document.getElementById("regEmail").value,
        password: document.getElementById("regPassword").value
    };

    fetch("http://localhost:8080/users", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(user)
    })
    .then(response => {
        if(response.ok){
            alert("Registration Successful");
            switchAuth('login');
        } else {
            alert("Registration Failed");
        }
    })
    .catch(error => console.error(error));
}

function logout() {
    currentUser = null;
    userProfile = {};
    bookings = [];
    localStorage.removeItem("token");
    showToast('👋 Logged out');
    showPage('loginPage');
    updateNavVisibility();
}
async function loadTheaters() {
    try {
        const response = await fetch("http://localhost:8080/theaters");

        if (!response.ok) {
            throw new Error("Failed to fetch theaters");
        }

        theaters = await response.json();

        console.log(theaters); // check data

        renderTheaters();

    } catch (error) {
        console.error(error);
        showToast("❌ Failed to load theaters");
    }
}
function renderTheaters() {

    const grid = document.getElementById("theatersGrid");

    if (!grid) return;

    grid.innerHTML = theaters.map(theater => `
        <div class="movie-card">
            <div class="poster">
                <i class="fas fa-building"></i>
            </div>

            <h3>${theater.name}</h3>

            <p>${theater.location}</p>
        </div>
    `).join("");

}

// ---------- RENDER MOVIES ----------
async function loadMovies() {
    try {
        const response = await fetch("http://localhost:8080/movies");

        if (!response.ok) {
            throw new Error("Failed to fetch movies");
        }

        movies = await response.json();

        console.log(movies);

        renderMovies();
        renderFeatured();

    } catch (error) {
        console.error(error);
        showToast("❌ Failed to load movies");
    }
}

function renderMovies(filter = '') {
    const grid = $('moviesGrid');
    let list = movies;
    const genre = $('genreFilter').value;
    if (genre) list = list.filter(m => m.genre === genre);
    if (filter) list = list.filter(m => m.title.toLowerCase().includes(filter.toLowerCase()));
    if (!list.length) { grid.innerHTML = '<p class="text-muted">No movies found</p>'; return; }
    grid.innerHTML = list.map(m => `
        <div class="movie-card" onclick="showDetails(${m.id})">
            <div class="poster"><i class="fas fa-film"></i></div>
            <h3>${m.title}</h3>
            <div class="meta"><span>${m.genre}</span><span>${m.language}</span><span>${m.duration}min</span></div>
            <div class="rating">⭐ ${m.rating}</div>
        </div>
    `).join('');
}

function renderFeatured() {
    const grid = $('featuredMovies');
    const featured = movies.slice(0, 4);
    grid.innerHTML = featured.map(m => `
        <div class="movie-card" onclick="showDetails(${m.id})">
            <div class="poster"><i class="fas fa-film"></i></div>
            <h3>${m.title}</h3>
            <div class="meta"><span>${m.genre}</span><span>⭐ ${m.rating}</span></div>
        </div>
    `).join('');
}

function searchMovies() {
    const q = $('searchInput').value;
    renderMovies(q);
    showPage('moviesPage');
}

function filterMovies() { renderMovies(); }

// ---------- DETAILS ----------
async function showDetails(movieId) {

    try {

        const movie = movies.find(m => m.id === movieId);
        const response = await fetch("http://localhost:8080/shows");
       const allShows = await response.json();

const shows = allShows.filter(
    show => show.movie.id === movieId
);

        currentMovieId = movieId;

        document.getElementById("detailsTitle").textContent =
            movie.title;

        document.getElementById("detailsGenre").textContent =
            movie.genre;

        document.getElementById("detailsLanguage").textContent =
            movie.language;

        document.getElementById("detailsDuration").textContent =
            movie.duration;

        document.getElementById("detailsRating").textContent =
            movie.rating;

        document.getElementById("detailsPlot").textContent =
            movie.plot;

        const showsDiv = document.getElementById("showsList");
showsDiv.innerHTML = shows.map(show => `
    <div class="show-card">
        <h4>${show.theater.name}</h4>
        <p>
            🎬 ${new Date(show.showTime).toLocaleString()}
        </p>
        <p>
            💰 ₹${show.ticketPrice}
        </p>

        <button class="btn btn-primary"
            onclick="openSeats(
                ${movieId},
                '${show.showTime}',
                ${show.id}
            )">
            Book Now
        </button>
    </div>
`).join('');

        showPage("detailsPage");

    } catch(error) {
        console.error(error);
        showToast("❌ Failed to load shows");
    }
}

// ---------- SEATS ----------
async function openSeats(movieId, time, showId) {
    if (!currentUser) {
        showToast("❌ Please login first");
        showPage("loginPage");
        return;
    }

    

    currentMovieId = movieId;
    currentShowTime = time;
    currentShowId = showId;

    const movie = movies.find(m => m.id === movieId);

    $('seatInfo').textContent =
        `${movie.title} | ${time}`;

    const response =
        await fetch(`http://localhost:8080/seats/show/${showId}`);

    const seats = await response.json();
    console.log(seats);

    const grid = $('seatsGrid');

    grid.innerHTML = '';

    seats.forEach(seatData => {

        const seat = document.createElement('div');

        seat.className = 'seat';

        seat.textContent =
            seatData.seatNumber;

        seat.dataset.id =
            seatData.id;

        if(seatData.booked){
            seat.classList.add('booked');
        } else {
            seat.classList.add('available');
        }

        seat.addEventListener(
            'click',
            () => toggleSeat(seat)
        );

        grid.appendChild(seat);
    });

    selectedSeats = [];

    updateSummary();

    showPage('seatsPage');
}

function toggleSeat(el) {

    if(el.classList.contains('booked'))
        return;

    el.classList.toggle('selected');

    const seatId = el.dataset.id;

    if(el.classList.contains('selected')) {

        if(!selectedSeats.includes(seatId))
            selectedSeats.push(seatId);

    } else {

        selectedSeats =
            selectedSeats.filter(
                id => id !== seatId
            );
    }

    updateSummary();
}


function updateSummary() {
    const count = selectedSeats.length;
    $('selectedSeatsCount').textContent = count;
    $('totalAmount').textContent = `₹${count * seatPrice}`;
}

// ---------- PAYMENT ----------
function switchPayment(method) {
    paymentMethod = method;
    document.querySelectorAll('.payment-tab').forEach(t => t.classList.remove('active'));
    document.querySelectorAll('.payment-form').forEach(f => f.classList.remove('active'));
    if (method === 'upi') {
        document.querySelector('.payment-tab:first-child').classList.add('active');
        $('upiForm').classList.add('active');
    } else {
        document.querySelector('.payment-tab:last-child').classList.add('active');
        $('cardForm').classList.add('active');
    }
}

function selectUpi(el, upiId) {
    document.querySelectorAll('.upi-btn').forEach(b => b.classList.remove('selected'));
    el.classList.add('selected');
    selectedUpi = upiId;
    $('upiId').value = upiId;
}

function processPayment() {
     if (!currentUser) {
        showToast("❌ Please login first");
        showPage("loginPage");
        return;
    }

    if (selectedSeats.length === 0) {
        showToast('❌ Please select at least one seat');
        return;
    }
    let isValid = true;
    let paymentDetails = '';
    if (paymentMethod === 'upi') {
        const upi = $('upiId').value.trim();
        if (!upi || !upi.includes('@')) {
            showToast('❌ Please enter a valid UPI ID (e.g., name@upi)');
            return;
        }
        paymentDetails = `UPI: ${upi}`;
    } else {
        const card = $('cardNumber').value.replace(/\s/g, '');
        const expiry = $('cardExpiry').value;
        const cvv = $('cardCvv').value;
        const name = $('cardName').value.trim();
        if (card.length < 15 || !expiry || !cvv || !name) {
            showToast('❌ Please fill all card details');
            return;
        }
        paymentDetails = `Card: ****${card.slice(-4)}`;
    }
    const movie = movies.find(m => m.id === currentMovieId);
    const details = `
        <p><strong>Movie:</strong> ${movie.title}</p>
        <p><strong>Show:</strong> ${currentShowTime}</p>
        <p><strong>Seats:</strong> ${selectedSeats.join(', ')}</p>
        <p><strong>Total:</strong> ₹${selectedSeats.length * seatPrice}</p>
        <p><strong>Payment:</strong> ${paymentDetails}</p>
        <p style="color:#16a34a; margin-top:0.5rem;">✅ Payment verified</p>
    `;
    $('confirmationDetails').innerHTML = details;
    $('confirmationModal').classList.add('show');
}

// ---------- MODAL / CONFIRM ----------
function closeModal() { $('confirmationModal').classList.remove('show'); }

async function completeBooking() {
     if (!currentUser) {
        showToast("❌ Please login first");
        showPage("loginPage");
        return;
    }


    try {

        // Book seats
        for (const seatId of selectedSeats) {

            await fetch(
                `http://localhost:8080/seats/book/${seatId}`,
                {
                    method: 'PUT',
                    headers: {
                        "Authorization": "Bearer " + localStorage.getItem("token")
                    }
                }
            );
        }

        // Create booking object
        const booking = {
    showId: currentShowId,
    seatsBooked: selectedSeats.length,
    amount: selectedSeats.length * seatPrice,
    paymentMethod: paymentMethod,
    paymentStatus: "SUCCESS"
};
        // Save booking in database
        await fetch("http://localhost:8080/bookings", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token")
            },
            body: JSON.stringify(booking)
        });

        closeModal();
showToast("🎫 Booking confirmed!");

await loadUserBookings();

showPage("bookingsPage");

    } catch(error) {

        console.error(error);

        showToast("❌ Booking failed");
    }
}
// ---------- BOOKINGS ----------
async function loadUserBookings() {
    try {
        if (!currentUser) return;

        const response = await fetch(
            "http://localhost:8080/bookings/my-bookings",
            {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("token")
                }
            }
        );

        if (!response.ok) {
            throw new Error("Failed to fetch bookings");
        }

        bookings = await response.json();

        console.log("Bookings:", bookings);

        renderBookings();

    } catch (error) {
        console.error(error);
        showToast("Failed to load bookings");
    }
}
function renderBookings() {

    const container = $('bookingsList');

    if (!bookings || bookings.length === 0) {
        container.innerHTML =
            '<p style="color:#64748b; padding:2rem;">No bookings yet.</p>';
        return;
    }

    container.innerHTML = bookings.map(b => `

        <div class="booking-card">

            <div class="info">

                <strong>
                    ${b.show?.movie?.title || 'Movie'}
                </strong>

                <span class="badge">
                    ${b.show?.showTime
                        ? new Date(b.show.showTime).toLocaleString()
                        : 'No Show Time'}
                </span>

                <span class="badge">
                    ${b.seatsBooked || 0} Seats
                </span>

                <span class="badge">
                    ${b.bookingDate
                        ? new Date(b.bookingDate).toLocaleDateString()
                        : 'No Date'}
                </span>

                <span class="badge paid">
                    ${b.payment?.paymentStatus || 'SUCCESS'}
                </span>

            </div>

        </div>

    `).join('');
}
function cancelBooking(id) {
    bookings = bookings.filter(b => b.id !== id);
    renderBookings();
    showToast('Booking cancelled');
    if (currentUser) renderProfile();
}

// ---------- PROFILE ----------
async function loadProfile() {

    try {

        const response = await fetch(
            "http://localhost:8080/users/current-profile",
            {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("token")
                }
            }
        );

        if (!response.ok) {
            throw new Error("Profile not found");
        }
const user = await response.json();

console.log("User Profile:", user);

userProfile = {
    id: user.id,
    name: user.name,
    email: user.email
};

        renderProfile();

    } catch(error) {

        console.error(error);

        showToast("Failed to load profile");
    }
}
function renderProfile() {

    console.log("Profile Data:", userProfile);

    if (!userProfile) return;

    const profileName = document.getElementById("profileName");
    const profileEmail = document.getElementById("profileEmail");
    const editName = document.getElementById("editName");
    const editEmail = document.getElementById("editEmail");

    if(profileName) profileName.innerText = userProfile.name || "No Name";
    if(profileEmail) profileEmail.innerText = userProfile.email || "No Email";
    if(editName) editName.value = userProfile.name || "";
    if(editEmail) editEmail.value = userProfile.email || "";
}
async function updateProfile(e) {

    e.preventDefault();

    const updatedUser = {
        name: $('editName').value,
        email: $('editEmail').value
    };

    await fetch(
        `http://localhost:8080/users/${userProfile.id}`,
        {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(updatedUser)
        }
    );

    showToast("Profile Updated");
}



// ---------- TOAST ----------
function showToast(msg) {
    const t = $('toast');
    t.textContent = msg;
    t.classList.add('show');
    clearTimeout(t._timer);
    t._timer = setTimeout(() => t.classList.remove('show'), 3000);
}

// ---------- INIT ----------
renderFeatured();
renderMovies();
loadMovies();
loadTheaters();
updateNavVisibility();
showPage('loginPage');