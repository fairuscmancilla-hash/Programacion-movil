$ErrorActionPreference = "Stop"
Set-Location $PSScriptRoot
$d = "app\src\main\res\drawable"
New-Item -ItemType Directory -Force $d | Out-Null
# Borra iconos de relleno que pudieran existir
Get-ChildItem $d -Filter "alumno_*" -ErrorAction SilentlyContinue | Remove-Item -Force
$fotos = @(
  "https://d8j0ntlcm91z4.cloudfront.net/user_3GpupJwnx0z06RbXz56ydSfUSul/hf_20260923_141003_1926a019-39a4-496f-86ab-eb1167aa9e84_min.webp"
  "https://d8j0ntlcm91z4.cloudfront.net/user_3GpupJwnx0z06RbXz56ydSfUSul/hf_20260923_141002_17526d72-06c0-4f40-9a94-641e5791348c_min.webp"
  "https://d8j0ntlcm91z4.cloudfront.net/user_3GpupJwnx0z06RbXz56ydSfUSul/hf_20260923_141003_123be483-21ab-4716-a304-9fa9aab4f17b_min.webp"
  "https://d8j0ntlcm91z4.cloudfront.net/user_3GpupJwnx0z06RbXz56ydSfUSul/hf_20260923_141004_291b2a4c-6357-4163-ba7c-8bed3fc8af86_min.webp"
  "https://d8j0ntlcm91z4.cloudfront.net/user_3GpupJwnx0z06RbXz56ydSfUSul/hf_20260923_141003_dcb66957-5637-485e-b8b5-41b95862fda1_min.webp"
  "https://d8j0ntlcm91z4.cloudfront.net/user_3GpupJwnx0z06RbXz56ydSfUSul/hf_20260923_141003_a7c7912f-6e46-4500-bbff-b83378e646da_min.webp"
  "https://d8j0ntlcm91z4.cloudfront.net/user_3GpupJwnx0z06RbXz56ydSfUSul/hf_20260923_141002_8163e9e1-f1ba-4ff1-ab3f-84fac3e9b1d9_min.webp"
  "https://d8j0ntlcm91z4.cloudfront.net/user_3GpupJwnx0z06RbXz56ydSfUSul/hf_20260923_141002_2c489fc1-e012-4e28-8b72-1477338f415f_min.webp"
)
for ($i = 0; $i -lt $fotos.Count; $i++) {
  $n = $i + 1
  Write-Host "Descargando alumno_$n.webp ..."
  Invoke-WebRequest -Uri $fotos[$i] -OutFile "$d\alumno_$n.webp" -UseBasicParsing
}
Write-Host ""
Write-Host "Listo: 8 fotos guardadas en $d" -ForegroundColor Green
