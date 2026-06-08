# 1. Get the last commit date to use as a reference
$lastCommitDateStr = git log -1 --format=%cd --date=iso 2>$null

if ([string]::IsNullOrEmpty($lastCommitDateStr)) {
    # If there are no commits yet, default to right now
    $baseDate = Get-Date
    Write-Host "No previous commits found. Using current date as baseline: $($baseDate.ToString('yyyy-MM-dd HH:mm:ss'))" -ForegroundColor Yellow
} else {
    $baseDate = [DateTime]::ParseExact($lastCommitDateStr.Substring(0, 19), "yyyy-MM-dd HH:mm:ss", $null)
    Write-Host "Last commit found: $($baseDate.ToString('yyyy-MM-dd HH:mm:ss'))" -ForegroundColor Cyan
}

# 2. Ask for time adjustments based on the last commit
Write-Host "`n--- TIME ADJUSTMENT ---" -ForegroundColor Blue
$daysToAdd = Read-Host "How many DAYS after the last commit? (Default: 0)"
if ([string]::IsNullOrEmpty($daysToAdd)) { $daysToAdd = 0 }

$hoursToAdd = Read-Host "How many HOURS after that? (Default: 1)"
if ([string]::IsNullOrEmpty($hoursToAdd)) { $hoursToAdd = 1 }

$minsToAdd = Read-Host "How many MINUTES after that? (Default: 30)"
if ([string]::IsNullOrEmpty($minsToAdd)) { $minsToAdd = 30 }

# Calculate the new fake date precisely down to the minute
$newDate = $baseDate.AddDays([int]$daysToAdd).AddHours([int]$hoursToAdd).AddMinutes([int]$minsToAdd)

# --- NEW: Randomize the seconds ---
# Subtract whatever seconds carried over, then add a random number between 0 and 59
$randomSeconds = Get-Random -Minimum 0 -Maximum 60
$newDate = $newDate.AddSeconds(-$newDate.Second).AddSeconds($randomSeconds)

$targetDateStr = $newDate.ToString("yyyy-MM-dd HH:mm:ss")
Write-Host "Target Commit Date will be: $targetDateStr" -ForegroundColor Green

# 3. Ask for the Author
Write-Host "`n--- AUTHOR SELECTION ---" -ForegroundColor Blue
Write-Host "1) Me (Default PC Git Config)"
Write-Host "2) Friend (Houdaifa Bahou)"
$authorChoice = Read-Host "Choose author (1 or 2, Default: 1)"

# 4. Ask for the Commit Message
Write-Host "`n--- COMMIT DETAILS ---" -ForegroundColor Blue
$msg = Read-Host "Enter commit message"
if ([string]::IsNullOrEmpty($msg)) {
    Write-Host "Error: Commit message cannot be empty!" -ForegroundColor Red
    Exit
}

# 5. Set environment variables and execute the commit
$env:GIT_AUTHOR_DATE = $targetDateStr
$env:GIT_COMMITTER_DATE = $targetDateStr

if ($authorChoice -eq "2") {
    # --- NEW: Prevent the split-identity issue on GitHub ---
    $env:GIT_COMMITTER_NAME = "Houdaifa Bahou"
    $env:GIT_COMMITTER_EMAIL = "bahouhoudaifa@gmail.com"
    
    git commit --author="Houdaifa Bahou <bahouhoudaifa@gmail.com>" -m "$msg"
    
    # Clean up identity variables immediately after
    $env:GIT_COMMITTER_NAME = $null
    $env:GIT_COMMITTER_EMAIL = $null
} else {
    git commit -m "$msg"
}

# Clean up date environment variables
$env:GIT_AUTHOR_DATE = $null
$env:GIT_COMMITTER_DATE = $null

Write-Host "`nDone! Commit created successfully." -ForegroundColor Green