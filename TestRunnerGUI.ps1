Add-Type -AssemblyName System.Windows.Forms
Add-Type -AssemblyName System.Drawing

# ========================================================
# THEME & CONSTANTS (Professional Enterprise Light)
# ========================================================
$Theme = @{
    Bg         = [System.Drawing.ColorTranslator]::FromHtml("#f9fafb")
    PanelBg    = [System.Drawing.ColorTranslator]::FromHtml("#ffffff")
    Fg         = [System.Drawing.ColorTranslator]::FromHtml("#111827")
    Muted      = [System.Drawing.ColorTranslator]::FromHtml("#6b7280")
    Border     = [System.Drawing.ColorTranslator]::FromHtml("#e5e7eb")
    PrimaryBg  = [System.Drawing.ColorTranslator]::FromHtml("#2563eb")
    PrimaryHov = [System.Drawing.ColorTranslator]::FromHtml("#1d4ed8")
    PrimaryFg  = [System.Drawing.ColorTranslator]::FromHtml("#ffffff")
    SecondBg   = [System.Drawing.ColorTranslator]::FromHtml("#f3f4f6")
    SecondHov  = [System.Drawing.ColorTranslator]::FromHtml("#e5e7eb")
    SecondFg   = [System.Drawing.ColorTranslator]::FromHtml("#374151")
    DangerBg   = [System.Drawing.ColorTranslator]::FromHtml("#ef4444")
    DangerHov  = [System.Drawing.ColorTranslator]::FromHtml("#dc2626")
    ConsoleBg  = [System.Drawing.ColorTranslator]::FromHtml("#0f172a")
    ConsoleFg  = [System.Drawing.ColorTranslator]::FromHtml("#e2e8f0")
    LogInfo    = [System.Drawing.ColorTranslator]::FromHtml("#10b981")
    LogWarn    = [System.Drawing.ColorTranslator]::FromHtml("#f59e0b")
    LogError   = [System.Drawing.ColorTranslator]::FromHtml("#ef4444")
}

$Fonts = @{
    Title   = New-Object System.Drawing.Font("Segoe UI", 14, [System.Drawing.FontStyle]::Bold)
    Sub     = New-Object System.Drawing.Font("Segoe UI", 9,  [System.Drawing.FontStyle]::Regular)
    Header  = New-Object System.Drawing.Font("Segoe UI", 9,  [System.Drawing.FontStyle]::Bold)
    Button  = New-Object System.Drawing.Font("Segoe UI", 9,  [System.Drawing.FontStyle]::Regular)
    Console = New-Object System.Drawing.Font("Consolas", 10, [System.Drawing.FontStyle]::Regular)
}

# ========================================================
# STATE & DATA
# ========================================================
$global:activeProcess = $null
$global:startTime = $null
$global:execButtons = @()

# Robust Log Tailing State
$global:logFile = "$env:TEMP\careconnect_exec.log"
$global:fs = $null
$global:sr = $null
$global:lineBuffer = ""

$Targets = @(
    @{ Display = "Module: Organization"; Group = "organization" },
    @{ Display = "Module: Formulary"; Group = "formulary" },
    @{ Display = "Module: CareConnect"; Group = "careconnect" },
    @{ Display = "Scenario: Creation Only"; Group = "create" },
    @{ Display = "Scenario: Validation Only"; Group = "validation" }
)

# ========================================================
# MAIN FORM
# ========================================================
$form = New-Object System.Windows.Forms.Form
$form.Text = "CareConnect Launcher"
$form.Size = New-Object System.Drawing.Size(1100, 750)
$form.StartPosition = "CenterScreen"
$form.BackColor = $Theme.Bg
$form.Font = $Fonts.Sub

$form.Add_FormClosing({
    if ($global:activeProcess -and -not $global:activeProcess.HasExited) {
        cmd.exe /c "taskkill /PID $($global:activeProcess.Id) /T /F" | Out-Null
    }
    if ($global:sr) { $global:sr.Dispose() }
    if ($global:fs) { $global:fs.Dispose() }
})

# ========================================================
# LEFT PANEL (Controls)
# ========================================================
$leftPanel = New-Object System.Windows.Forms.Panel
$leftPanel.Width = 320
$leftPanel.Dock = [System.Windows.Forms.DockStyle]::Left
$leftPanel.BackColor = $Theme.PanelBg
$leftPanel.Padding = New-Object System.Windows.Forms.Padding(20)
$form.Controls.Add($leftPanel)

$borderRight = New-Object System.Windows.Forms.Panel
$borderRight.Width = 1
$borderRight.Dock = [System.Windows.Forms.DockStyle]::Right
$borderRight.BackColor = $Theme.Border
$leftPanel.Controls.Add($borderRight)

# Header
$lblTitle = New-Object System.Windows.Forms.Label
$lblTitle.Text = "CareConnect Launcher"
$lblTitle.Font = $Fonts.Title
$lblTitle.ForeColor = $Theme.Fg
$lblTitle.AutoSize = $true
$lblTitle.Location = New-Object System.Drawing.Point(20, 20)
$leftPanel.Controls.Add($lblTitle)

$lblSub = New-Object System.Windows.Forms.Label
$lblSub.Text = "QA Automation Framework"
$lblSub.ForeColor = $Theme.Muted
$lblSub.AutoSize = $true
$lblSub.Location = New-Object System.Drawing.Point(22, 50)
$leftPanel.Controls.Add($lblSub)

# Helper UI Functions
function Create-Label($text, $y) {
    $lbl = New-Object System.Windows.Forms.Label
    $lbl.Text = $text
    $lbl.Font = $Fonts.Header
    $lbl.AutoSize = $true
    $lbl.Location = New-Object System.Drawing.Point(20, $y)
    $leftPanel.Controls.Add($lbl)
}

function Create-Button($text, $y, $isPrimary, $cmd) {
    $btn = New-Object System.Windows.Forms.Button
    $btn.Text = $text
    $btn.Font = $Fonts.Button
    $btn.Size = New-Object System.Drawing.Size(275, 36)
    $btn.Location = New-Object System.Drawing.Point(20, $y)
    $btn.FlatStyle = [System.Windows.Forms.FlatStyle]::Flat
    $btn.FlatAppearance.BorderSize = 0
    $btn.Cursor = [System.Windows.Forms.Cursors]::Hand

    $bg = if ($isPrimary) { $Theme.PrimaryBg } else { $Theme.SecondBg }
    $hov = if ($isPrimary) { $Theme.PrimaryHov } else { $Theme.SecondHov }
    $fg = if ($isPrimary) { $Theme.PrimaryFg } else { $Theme.SecondFg }

    $btn.BackColor = $bg; $btn.ForeColor = $fg
    $btn.Tag = @{ Bg = $bg; Fg = $fg }

    $btn.Add_MouseEnter({ if ($this.Enabled) { $this.BackColor = $hov } }.GetNewClosure())
    $btn.Add_MouseLeave({ if ($this.Enabled) { $this.BackColor = $bg } }.GetNewClosure())
    $btn.Add_Click({ Run-Command $cmd }.GetNewClosure())
    
    $leftPanel.Controls.Add($btn)
    $global:execButtons += $btn
}

$y = 100
Create-Label "Execution" $y
$y += 25; Create-Button "[ Run All Tests (Full Suite) ]" $y $true "mvn clean test"
$y += 45; Create-Button "[ Run Regression Suite ]" $y $false "mvn clean test -Dsurefire.suiteXmlFiles= -Dgroups=regression"

$y += 60
Create-Label "Targeted Execution" $y
$y += 25
$combo = New-Object System.Windows.Forms.ComboBox
$combo.Size = New-Object System.Drawing.Size(275, 30)
$combo.Location = New-Object System.Drawing.Point(20, $y)
$combo.DropDownStyle = [System.Windows.Forms.ComboBoxStyle]::DropDownList
$combo.BackColor = $Theme.SecondBg
foreach ($t in $Targets) { $combo.Items.Add($t.Display) | Out-Null }
$combo.SelectedIndex = 0
$leftPanel.Controls.Add($combo)

$y += 35
$btnTarget = New-Object System.Windows.Forms.Button
$btnTarget.Text = "[ Run Target ]"
$btnTarget.Size = New-Object System.Drawing.Size(275, 36)
$btnTarget.Location = New-Object System.Drawing.Point(20, $y)
$btnTarget.FlatStyle = [System.Windows.Forms.FlatStyle]::Flat
$btnTarget.FlatAppearance.BorderSize = 0
$btnTarget.BackColor = $Theme.SecondBg
$btnTarget.ForeColor = $Theme.SecondFg
$btnTarget.Cursor = [System.Windows.Forms.Cursors]::Hand
$btnTarget.Tag = @{ Bg = $Theme.SecondBg; Fg = $Theme.SecondFg }
$btnTarget.Add_MouseEnter({ if ($this.Enabled) { $this.BackColor = $Theme.SecondHov } })
$btnTarget.Add_MouseLeave({ if ($this.Enabled) { $this.BackColor = $Theme.SecondBg } })
$btnTarget.Add_Click({
    $target = $Targets | Where-Object { $_.Display -eq $combo.SelectedItem }
    Run-Command "mvn clean test -Dsurefire.suiteXmlFiles= -Dgroups=$($target.Group)"
})
$leftPanel.Controls.Add($btnTarget)
$global:execButtons += $btnTarget

$y += 60
Create-Label "Reports" $y
$y += 25; Create-Button "[ Open Allure Report ]" $y $false "mvn allure:serve"

$y += 60
Create-Label "Maintenance" $y
$y += 25; Create-Button "[ Clean Workspace ]" $y $false "mvn clean"


# ========================================================
# RIGHT PANEL (Console area)
# ========================================================
$rightPanel = New-Object System.Windows.Forms.Panel
$rightPanel.Dock = [System.Windows.Forms.DockStyle]::Fill
$rightPanel.BackColor = $Theme.Bg
$rightPanel.Padding = New-Object System.Windows.Forms.Padding(20)
$form.Controls.Add($rightPanel)

# Top Toolbar
$toolbar = New-Object System.Windows.Forms.Panel
$toolbar.Height = 40
$toolbar.Dock = [System.Windows.Forms.DockStyle]::Top
$rightPanel.Controls.Add($toolbar)

$lblConsole = New-Object System.Windows.Forms.Label
$lblConsole.Text = ">_ Execution Console"
$lblConsole.Font = $Fonts.Header
$lblConsole.AutoSize = $true
$lblConsole.Location = New-Object System.Drawing.Point(0, 10)
$toolbar.Controls.Add($lblConsole)

function Create-ToolbarBtn($text, $x) {
    $b = New-Object System.Windows.Forms.Button
    $b.Text = $text
    $b.Size = New-Object System.Drawing.Size(90, 30)
    $b.Location = New-Object System.Drawing.Point($x, 0)
    $b.FlatStyle = [System.Windows.Forms.FlatStyle]::Flat
    $b.FlatAppearance.BorderSize = 1
    $b.FlatAppearance.BorderColor = $Theme.Border
    $b.BackColor = $Theme.PanelBg
    $b.Cursor = [System.Windows.Forms.Cursors]::Hand
    $toolbar.Controls.Add($b)
    return $b
}

$btnSave = Create-ToolbarBtn "Save Log" 630
$btnSave.Anchor = [System.Windows.Forms.AnchorStyles]::Top -bor [System.Windows.Forms.AnchorStyles]::Right
$btnSave.Add_Click({
    $dlg = New-Object System.Windows.Forms.SaveFileDialog
    $dlg.Filter = "Log Files (*.log)|*.log|All Files (*.*)|*.*"
    if ($dlg.ShowDialog() -eq [System.Windows.Forms.DialogResult]::OK) {
        Set-Content -Path $dlg.FileName -Value $rtb.Text
    }
})

$btnCopy = Create-ToolbarBtn "Copy" 530
$btnCopy.Anchor = [System.Windows.Forms.AnchorStyles]::Top -bor [System.Windows.Forms.AnchorStyles]::Right
$btnCopy.Add_Click({ if ($rtb.TextLength -gt 0) { [System.Windows.Forms.Clipboard]::SetText($rtb.Text) } })

$btnClear = Create-ToolbarBtn "Clear" 430
$btnClear.Anchor = [System.Windows.Forms.AnchorStyles]::Top -bor [System.Windows.Forms.AnchorStyles]::Right
$btnClear.Add_Click({ $rtb.Clear() })

# Console Text Box
$rtbPanel = New-Object System.Windows.Forms.Panel
$rtbPanel.Dock = [System.Windows.Forms.DockStyle]::Fill
$rtbPanel.Padding = New-Object System.Windows.Forms.Padding(0, 10, 0, 10)
$rightPanel.Controls.Add($rtbPanel)
$rtbPanel.BringToFront()

$rtb = New-Object System.Windows.Forms.RichTextBox
$rtb.Dock = [System.Windows.Forms.DockStyle]::Fill
$rtb.BackColor = $Theme.ConsoleBg
$rtb.ForeColor = $Theme.ConsoleFg
$rtb.Font = $Fonts.Console
$rtb.ReadOnly = $true
$rtb.BorderStyle = [System.Windows.Forms.BorderStyle]::None
$rtb.HideSelection = $false
$rtbPanel.Controls.Add($rtb)

# Bottom Progress Area
$bottomPanel = New-Object System.Windows.Forms.Panel
$bottomPanel.Height = 70
$bottomPanel.Dock = [System.Windows.Forms.DockStyle]::Bottom
$rightPanel.Controls.Add($bottomPanel)

$lblStatus = New-Object System.Windows.Forms.Label
$lblStatus.Text = "Ready"
$lblStatus.AutoSize = $true
$lblStatus.Location = New-Object System.Drawing.Point(0, 10)
$bottomPanel.Controls.Add($lblStatus)

$lblElapsed = New-Object System.Windows.Forms.Label
$lblElapsed.Text = "Elapsed: 00:00:00"
$lblElapsed.AutoSize = $true
$lblElapsed.Location = New-Object System.Drawing.Point(620, 10)
$lblElapsed.Anchor = [System.Windows.Forms.AnchorStyles]::Top -bor [System.Windows.Forms.AnchorStyles]::Right
$bottomPanel.Controls.Add($lblElapsed)

$progBar = New-Object System.Windows.Forms.ProgressBar
$progBar.Size = New-Object System.Drawing.Size(600, 15)
$progBar.Location = New-Object System.Drawing.Point(0, 35)
$progBar.Anchor = [System.Windows.Forms.AnchorStyles]::Top -bor [System.Windows.Forms.AnchorStyles]::Left -bor [System.Windows.Forms.AnchorStyles]::Right
$progBar.Style = [System.Windows.Forms.ProgressBarStyle]::Blocks
$bottomPanel.Controls.Add($progBar)

$btnStop = New-Object System.Windows.Forms.Button
$btnStop.Text = "[ Stop ]"
$btnStop.Size = New-Object System.Drawing.Size(120, 36)
$btnStop.Location = New-Object System.Drawing.Point(615, 25)
$btnStop.Anchor = [System.Windows.Forms.AnchorStyles]::Top -bor [System.Windows.Forms.AnchorStyles]::Right
$btnStop.FlatStyle = [System.Windows.Forms.FlatStyle]::Flat
$btnStop.FlatAppearance.BorderSize = 0
$btnStop.BackColor = $Theme.DangerBg
$btnStop.ForeColor = $Theme.PrimaryFg
$btnStop.Enabled = $false
$btnStop.Cursor = [System.Windows.Forms.Cursors]::Hand
$btnStop.Add_Click({
    if ($global:activeProcess -and -not $global:activeProcess.HasExited) {
        cmd.exe /c "taskkill /PID $($global:activeProcess.Id) /T /F" | Out-Null
        Append-Log "Execution Terminated by User."
    }
})
$bottomPanel.Controls.Add($btnStop)

# ========================================================
# ROBUST EXECUTION LOGIC (Timer-based File Tailing)
# ========================================================
function Append-Log($line) {
    if ([string]::IsNullOrWhiteSpace($line)) { return }
    $rtb.SelectionStart = $rtb.TextLength
    $rtb.SelectionLength = 0
    
    if ($line -match "ERROR|FAILURE|FAILED|FAIL") { $rtb.SelectionColor = $Theme.LogError }
    elseif ($line -match "WARN") { $rtb.SelectionColor = $Theme.LogWarn }
    elseif ($line -match "INFO|SUCCESS|PASS") { $rtb.SelectionColor = $Theme.LogInfo }
    else { $rtb.SelectionColor = $Theme.ConsoleFg }
    
    $rtb.AppendText("[$([DateTime]::Now.ToString('HH:mm:ss'))] $line`r`n")
    $rtb.ScrollToCaret()
}

$timer = New-Object System.Windows.Forms.Timer
$timer.Interval = 200
$timer.Add_Tick({
    if ($global:activeProcess) {
        $ts = [DateTime]::Now - $global:startTime
        $lblElapsed.Text = "Elapsed: " + $ts.ToString("hh\:mm\:ss")
        
        if ($global:sr) {
            $newText = $global:sr.ReadToEnd()
            if ($newText) {
                $global:lineBuffer += $newText
                if ($global:lineBuffer -match "`n") {
                    $parts = $global:lineBuffer -split "`r?`n"
                    $global:lineBuffer = $parts[-1]
                    for ($i = 0; $i -lt ($parts.Length - 1); $i++) {
                        Append-Log $parts[$i]
                    }
                }
            }
        }
        
        if ($global:activeProcess.HasExited) {
            $timer.Stop()
            $code = $global:activeProcess.ExitCode
            
            # Read any final remaining text
            if ($global:sr) {
                $newText = $global:sr.ReadToEnd()
                if ($newText) { $global:lineBuffer += $newText }
                if ($global:lineBuffer) {
                    $parts = $global:lineBuffer -split "`r?`n"
                    foreach ($p in $parts) { Append-Log $p }
                }
                $global:sr.Dispose()
                $global:fs.Dispose()
                $global:sr = $null
                $global:fs = $null
            }
            
            $progBar.Style = [System.Windows.Forms.ProgressBarStyle]::Blocks
            $progBar.Value = 100
            $btnStop.Enabled = $false
            
            foreach ($b in $global:execButtons) {
                $b.Enabled = $true
                $b.BackColor = $b.Tag.Bg
            }
            
            if ($code -eq 0) {
                $lblStatus.Text = "Completed Successfully"
                $lblStatus.ForeColor = $Theme.LogInfo
                Append-Log "`n--- EXECUTION SUCCESSFUL ---"
            } else {
                $lblStatus.Text = "Failed (Exit Code: $code)"
                $lblStatus.ForeColor = $Theme.LogError
                Append-Log "`n--- EXECUTION FAILED (Exit Code: $code) ---"
            }
            $global:activeProcess.Dispose()
            $global:activeProcess = $null
        }
    }
})

function Run-Command($cmd) {
    if ($global:activeProcess) { return }
    
    $rtb.Clear()
    Append-Log "Starting execution: $cmd"
    Append-Log "=================================================="
    
    foreach ($b in $global:execButtons) { $b.Enabled = $false; $b.BackColor = $Theme.Border }
    $btnStop.Enabled = $true
    
    $lblStatus.Text = "Running..."
    $lblStatus.ForeColor = $Theme.Fg
    $lblElapsed.Text = "Elapsed: 00:00:00"
    $progBar.Style = [System.Windows.Forms.ProgressBarStyle]::Marquee
    $progBar.Value = 0
    
    Set-Content -Path $global:logFile -Value "" -Force
    $global:fs = [System.IO.FileStream]::new($global:logFile, [System.IO.FileMode]::Open, [System.IO.FileAccess]::Read, [System.IO.FileShare]::ReadWrite)
    $global:sr = [System.IO.StreamReader]::new($global:fs)
    $global:lineBuffer = ""
    
    $global:startTime = [DateTime]::Now
    
    $pInfo = New-Object System.Diagnostics.ProcessStartInfo
    $pInfo.FileName = "cmd.exe"
    $pInfo.Arguments = "/c `"$cmd > `"$global:logFile`" 2>&1`""
    $pInfo.RedirectStandardOutput = $false
    $pInfo.RedirectStandardError = $false
    $pInfo.UseShellExecute = $false
    $pInfo.CreateNoWindow = $true
    
    $global:activeProcess = New-Object System.Diagnostics.Process
    $global:activeProcess.StartInfo = $pInfo
    $global:activeProcess.Start() | Out-Null
    
    $timer.Start()
}

[void]$form.ShowDialog()
