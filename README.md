parse_json() {
    local json=$1
    local output=""

    # Loop through JSON keys
    while IFS= read -r line; do
        output="${output}${line}\\n"
    done <<< "$(jq -r 'to_entries[] | "\(.key)=\(.value.value)"' <<< "$json")"

    # Print output without trailing newline
    printf "%s" "$output"

    echo $output
    
}

# Parse input JSON
parse_json "$input_json"
