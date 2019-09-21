
group "Windows filesystem management" do

  target "Create folder 'hola' in your home"
  goto :localhost, :exec => "dir /a:d %USERPROFILE%"
  expect "hola"

end

play do
  show
  export
end
